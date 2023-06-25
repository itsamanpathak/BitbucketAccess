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
                if(it.failedType == Repository.TYPE.GET_REPO_LIST){
                    showProgress.value = false
                    event.value = HomeEvent.ShowSnackBar(it.errorValue, it.errorMessage)
                }
            }
            else -> {}
        }
    }



    init {
        showProgress.value = true
        repo.fetchRepoList()
        repo.event.observeForever(repoObserver)
    }

    override fun onCleared() {
        super.onCleared()
        repo.event.removeObserver(repoObserver)
    }


    sealed class HomeEvent {
        data class ShowSnackBar(val message: Int, val messageString: String?) : HomeEvent()
    }

}