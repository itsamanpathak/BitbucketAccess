package com.amanpathak.bitbucketaccess.ui.signin

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.R
import com.amanpathak.bitbucketaccess.databinding.ActivitySigninBinding
import com.amanpathak.bitbucketaccess.utils.AppNavigator
import com.amanpathak.bitbucketaccess.utils.SharedPreferenceManager
import com.google.android.material.snackbar.Snackbar

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private val viewModel : SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        binding.root.isVisible = false
        binding.lifecycleOwner = this
        binding.vm = viewModel
        setContentView(binding.root)

        val isSignedIn =
            SharedPreferenceManager.getBoolean(SharedPreferenceManager.KEY_IS_SIGNED_IN)
        if (isSignedIn) {
            AppNavigator.navigateToHomeActivity(this, null)
        } else {
            binding.root.isVisible = true
        }

        listeners()

    }

    private fun listeners() {

        viewModel.event.observe(this, Observer {

            when(it){

                is SignInViewModel.SignInEvent.OnSignInSuccess -> {
                    Snackbar.make(findViewById(android.R.id.content),it.messageString, Snackbar.LENGTH_LONG).show()
                    AppNavigator.navigateToHomeActivity(activity = this, null)
                }
                is SignInViewModel.SignInEvent.ShowSnackBar -> {
                    if(!it.messageString.isNullOrEmpty())   {
                        Snackbar.make(findViewById(android.R.id.content),it.messageString, Snackbar.LENGTH_LONG).show()
                    }
                }
                else -> {}
            }

        })


    }
}