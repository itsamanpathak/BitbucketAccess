package com.amanpathak.bitbucketaccess.network

import android.content.Context
import com.amanpathak.bitbucketaccess.utils.SharedPreferenceManager
import com.amanpathak.bitbucketaccess.utils.SharedPreferenceManager.KEY_PASSWORD
import com.amanpathak.bitbucketaccess.utils.SharedPreferenceManager.KEY_USERNAME
import com.chuckerteam.chucker.BuildConfig
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient(private val context: Context) {
    private var retrofit: Retrofit? = null


    fun api() : Api  {
        val client = OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(SharedPreferenceManager.getString(KEY_USERNAME), SharedPreferenceManager.getString(
                KEY_PASSWORD)))
            .addInterceptor(ChuckerInterceptor(context))
            .build()

        val baseUrl = "https://api.bitbucket.org/2.0/"
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(Api::class.java)
    }
}