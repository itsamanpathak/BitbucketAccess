package com.amanpathak.bitbucketaccess.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

object Utils {

    fun convertDate_DD_MM_YYYY(dateString: String) : String {
            if(dateString.isNullOrEmpty()) return ""

            val date = dateString.split(".")

            var spf =  SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss").parse(date[0])
            val simpleDateFormat = SimpleDateFormat("dd MMM YYYY")
            return simpleDateFormat.format(spf)
    }

    fun isNetworkConnected(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }



}