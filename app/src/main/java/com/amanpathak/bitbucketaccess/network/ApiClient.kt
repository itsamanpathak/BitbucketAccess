package com.amanpathak.bitbucketaccess.network

import android.content.Context
import android.os.Build.HOST
import com.amanpathak.bitbucketaccess.R
import com.amanpathak.bitbucketaccess.utils.SharedPreferenceManager
import com.amanpathak.bitbucketaccess.utils.SharedPreferenceManager.KEY_PASSWORD
import com.amanpathak.bitbucketaccess.utils.SharedPreferenceManager.KEY_USERNAME
import com.chuckerteam.chucker.BuildConfig
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient(private val context: Context) {
    private var retrofit: Retrofit? = null


    fun api() : Api  {

        val host = "api.bitbucket.org"

        val  certificatePinner = CertificatePinner.Builder()
            .add(host,"sha256/XBn9ShlHgDj6XrYy6jjLI11pxHVRCAgdaIbvIgeVdgA=")
            .build()


        val client = OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .addInterceptor(BasicAuthInterceptor(SharedPreferenceManager.getString(KEY_USERNAME), SharedPreferenceManager.getString(
                KEY_PASSWORD)))
            .addInterceptor(ChuckerInterceptor(context))
            .build()

        val baseUrl = "https://$host/2.0/"
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