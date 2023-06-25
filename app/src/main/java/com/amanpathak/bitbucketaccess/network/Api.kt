package com.amanpathak.bitbucketaccess.network

import androidx.annotation.Keep
import com.amanpathak.bitbucketaccess.model.SignInModel
import com.amanpathak.bitbucketaccess.network.model.RepoListNetworkResponse
import com.amanpathak.bitbucketaccess.network.model.SignInNetworkResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


@Keep
interface Api {

    @GET("user")
    @Headers("Accept: application/json")
    fun signInToBitbucket(
    ): Call<SignInNetworkResponse>

    @GET("repositories/{workspace}")
    @Headers("Accept: application/json")
    fun fetchRepositoriesList(
        @Path( value = "workspace", encoded = true) workspace : String
    ) : Call<RepoListNetworkResponse>

}