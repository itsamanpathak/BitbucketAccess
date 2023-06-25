package com.amanpathak.bitbucketaccess.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.amanpathak.bitbucketaccess.ui.HomeActivity
import com.amanpathak.bitbucketaccess.ui.home.RepoDetailScreen
import com.amanpathak.bitbucketaccess.ui.signin.SignInActivity

object AppNavigator {


    fun navigateToSignIn(activity: Activity, bundle: Bundle?){
        activity.launchActivity<SignInActivity> {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        activity.finish()
    }

    fun navigateToHomeActivity(activity: Activity, bundle: Bundle?){
        activity.launchActivity<HomeActivity> {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        activity.finish()
    }

    fun navigateToRepoDetail(activity: Activity, bundle: Bundle?){
        activity.launchActivity<RepoDetailScreen> {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            bundle?.let { this.putExtras(it) }
        }
    }





    //Helper Methods -------------------------------------------
    private inline fun <reified T : Any> Activity.launchActivity(
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}
    ) {
        val intent = newIntent<T>(this)
        intent.init()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options)
        } else {
            startActivity(intent)
        }
    }

    private inline fun <reified T : Any> newIntent(context: Context): Intent =
        Intent(context, T::class.java)


}