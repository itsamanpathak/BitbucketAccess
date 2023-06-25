package com.amanpathak.bitbucketaccess

import android.app.Application
import com.amanpathak.bitbucketaccess.utils.SharedPreferenceManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferenceManager.initialize(context = applicationContext)
    }
}