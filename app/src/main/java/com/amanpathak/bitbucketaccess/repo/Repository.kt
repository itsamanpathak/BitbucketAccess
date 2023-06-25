package com.amanpathak.bitbucketaccess.repo

import androidx.annotation.Keep
import com.amanpathak.bitbucketaccess.R
import com.amanpathak.bitbucketaccess.model.RepoModel
import com.amanpathak.bitbucketaccess.model.SignInModel
import com.amanpathak.bitbucketaccess.network.ApiClient
import com.amanpathak.bitbucketaccess.network.model.RepoListNetworkResponse
import com.amanpathak.bitbucketaccess.network.model.SignInNetworkResponse
import com.amanpathak.bitbucketaccess.utils.SharedPreferenceManager
import com.amanpathak.bitbucketaccess.utils.SingleLiveEvent
import com.amanpathak.bitbucketaccess.utils.Utils
import retrofit2.Call
import retrofit2.Response

class Repository(private val apiClient: ApiClient) {
    val event = SingleLiveEvent<RepoEvent>()
    var currentTYPE : TYPE = TYPE.SIGN_IN

    enum class TYPE {
        SIGN_IN, GET_REPO_LIST
    }

    fun callBitbucketSignIn(userName : String, password : String){

        SharedPreferenceManager.setString(SharedPreferenceManager.KEY_USERNAME, userName)
        SharedPreferenceManager.setString(SharedPreferenceManager.KEY_PASSWORD, password)

        currentTYPE = TYPE.SIGN_IN

        apiClient.api().
        signInToBitbucket()
            .enqueue(object : retrofit2.Callback<SignInNetworkResponse> {
                override fun onResponse(
                    call: Call<SignInNetworkResponse>,
                    response: Response<SignInNetworkResponse>
                ) {
                    if (response.isSuccessful) {
                        mapRemoteToLocaleModels(response.body())
                    }
                    else
                    {
                        event.value = RepoEvent.OnError(TYPE.SIGN_IN, "", R.string.generic_error_message)
                    }
                }

                override fun onFailure(call: Call<SignInNetworkResponse>, t: Throwable) {
                    event.value = RepoEvent.OnError(TYPE.SIGN_IN,
                        t.message.toString(), R.string.generic_error_message)
                }
            })
    }

    fun fetchRepoList(){

        currentTYPE = TYPE.GET_REPO_LIST


        /*
            Here static workspace name is used, although this can be passed dynamically
            by fetching list of workspace and then the list of repo using the workspace name
        */
        val staticWorkspace = "kfh-bitbucketaccess"

        apiClient.api().
        fetchRepositoriesList(staticWorkspace)
            .enqueue(object : retrofit2.Callback<RepoListNetworkResponse> {
                override fun onResponse(
                    call: Call<RepoListNetworkResponse>,
                    response: Response<RepoListNetworkResponse>
                ) {
                    if (response.isSuccessful) {
                        mapRemoteToLocaleModels(response.body())
                    }
                    else
                    {
                        event.value = RepoEvent.OnError(TYPE.GET_REPO_LIST, "", R.string.generic_error_message)
                    }
                }

                override fun onFailure(call: Call<RepoListNetworkResponse>, t: Throwable) {
                    event.value = RepoEvent.OnError(TYPE.GET_REPO_LIST,
                        t.message.toString(), R.string.generic_error_message)
                }
            })
    }

    fun onRetry(){
        when(currentTYPE){

            TYPE.GET_REPO_LIST -> {
               fetchRepoList()
            }

            else -> {}
        }
    }



    private fun mapRemoteToLocaleModels(response: Any?) {
       if(response == null){ return }

        when(currentTYPE){

            TYPE.SIGN_IN -> {

                if(response is SignInNetworkResponse){
                    val  avatar = response.links?.avatar?.avatarLink
                    val model = SignInModel(avatar, response.displayName, response.userName, response.accountStatus, response.createdOn)
                    event.value = RepoEvent.OnSuccessSignIn(model)
                }
            }
            TYPE.GET_REPO_LIST -> {

                if(response is RepoListNetworkResponse){
                    val repoModelList = arrayListOf<RepoModel>()

                    for (i in response.values.listIterator()){
                        repoModelList.add(RepoModel(i.id,
                            i.links.avatar?.avatarLink.toString(), i.name, i.desc, i.isPrivate, i.createdOn, i.workspace.name))
                    }

                    event.value = RepoEvent.OnRepoListFetched(repoModelList)
                }
            }


        }








    }



    sealed class RepoEvent {
        data class OnSuccessSignIn(val model : SignInModel) : RepoEvent()
        data class OnRepoListFetched(val repoList : List<RepoModel>) : RepoEvent()
        data class OnError(val failedType : TYPE, val errorMessage: String?, val errorValue: Int) : RepoEvent()
    }






}