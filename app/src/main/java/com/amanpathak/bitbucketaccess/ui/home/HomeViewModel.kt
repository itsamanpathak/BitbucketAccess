package com.amanpathak.bitbucketaccess.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.amanpathak.bitbucketaccess.R
import com.amanpathak.bitbucketaccess.model.RepoModel
import com.amanpathak.bitbucketaccess.network.ApiClient
import com.amanpathak.bitbucketaccess.network.model.RepoListItem
import com.amanpathak.bitbucketaccess.repo.Repository
import com.amanpathak.bitbucketaccess.ui.signin.SignInViewModel
import com.amanpathak.bitbucketaccess.utils.SharedPreferenceManager
import com.amanpathak.bitbucketaccess.utils.SingleLiveEvent
import com.amanpathak.bitbucketaccess.utils.Utils

class HomeViewModel(appContext : Application) : AndroidViewModel(appContext) {

    val repoList = MutableLiveData(listOf<RepoModel>())
    var event = SingleLiveEvent<HomeEvent>()
    var repo = Repository(ApiClient(appContext))
    var showProgress = MutableLiveData<Boolean>(false)


    private val repoObserver = Observer<Repository.RepoEvent> {
        when(it){

            is Repository.RepoEvent.OnRepoListFetched -> {
                showProgress.value = false
                repoList.value = it.repoList
            }
            is Repository.RepoEvent.OnError -> {
                showProgress.value = false
                if(it.failedType == Repository.TYPE.GET_REPO_LIST && Utils.isNetworkConnected(appContext)){
                    event.value = HomeEvent.ShowSnackBar(it.errorValue, it.errorMessage)
                }
                else if(!Utils.isNetworkConnected(appContext)){
                    event.value = HomeEvent.InternetNotConnected(appContext.getString(R.string.internet_not_connected), true)
                }
            }
            else -> {}
        }
    }



    init {
        showProgress.value = true
        repo.event.observeForever(repoObserver)
    }

    override fun onCleared() {
        super.onCleared()
        repo.event.removeObserver(repoObserver)
    }

    fun onRetryClick(){
        showProgress.value = true
        repo.onRetry()
    }

    fun fetchRepoList(){
        repo.fetchRepoList()
    }


    sealed class HomeEvent {
        data class InternetNotConnected(val messageString: String?, val showRetry : Boolean) : HomeEvent()
        data class ShowSnackBar(val message: Int, val messageString: String?) : HomeEvent()
    }

}