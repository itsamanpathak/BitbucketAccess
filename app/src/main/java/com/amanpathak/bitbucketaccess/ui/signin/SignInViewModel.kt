package com.amanpathak.bitbucketaccess.ui.signin

import android.app.Application
import android.location.Location
import android.os.Looper
import android.text.Spanned
import android.text.TextUtils
import androidx.core.text.HtmlCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.amanpathak.bitbucketaccess.R
import com.amanpathak.bitbucketaccess.network.Api
import com.amanpathak.bitbucketaccess.network.ApiClient
import com.amanpathak.bitbucketaccess.repo.Repository
import com.amanpathak.bitbucketaccess.utils.SharedPreferenceManager
import com.amanpathak.bitbucketaccess.utils.SharedPreferenceManager.KEY_PROFILE_DETAIL
import com.amanpathak.bitbucketaccess.utils.SingleLiveEvent


class SignInViewModel(val appContext: Application) : AndroidViewModel(appContext) {
    val TAG = SignInViewModel::class.java.name
    val repo = Repository(ApiClient(appContext))
    val event = SingleLiveEvent<SignInEvent>()

    var userName = MutableLiveData<String>("amanpathak730")
    var password = MutableLiveData<String>("ATBB3F4avnZzAv5Zf4YHKRHAH7kb84DE6781")
    var registerHelperText = MutableLiveData<Spanned>()
    var showProgress = MutableLiveData<Boolean>(false)




    private val repoObserver = Observer<Repository.RepoEvent> {
        when(it){

            is Repository.RepoEvent.OnSuccessSignIn -> {
                showProgress.value = false
                SharedPreferenceManager.setBoolean(SharedPreferenceManager.KEY_IS_SIGNED_IN, true)
                SharedPreferenceManager.setObject(KEY_PROFILE_DETAIL,it.model)
                event.value = SignInEvent.OnSignInSuccess(appContext.getString(R.string.signed_in_success))
            }
            is Repository.RepoEvent.OnError -> {
                showProgress.value = false
                if(it.failedType == Repository.TYPE.SIGN_IN){
                    event.value = SignInEvent.ShowSnackBar(it.errorValue, it.errorMessage)
                }
            }
            else -> {}
        }
    }

    init {
        registerHelperText.value = HtmlCompat.fromHtml(appContext.getString(R.string.register_helper_text),
            HtmlCompat.FROM_HTML_MODE_LEGACY)
        repo.event.observeForever(repoObserver)
    }

    fun onSignInClick(){
        val isValid = validateInputs()
        if(isValid){
            showProgress.value = true
            repo.callBitbucketSignIn(userName.value.toString(), password.value.toString())
        }
        else
        {
            event.value = SignInEvent.ShowSnackBar(-1, appContext.getString(R.string.valid_credentials))
        }
    }

    private fun validateInputs() : Boolean {
        var isValid = true
        if(userName.value.isNullOrEmpty()){
            isValid = false
        }
        else if(password.value.isNullOrEmpty()){
            isValid = false
        }
        return isValid
    }


    override fun onCleared() {
        super.onCleared()
        repo.event.removeObserver(repoObserver)
    }


    sealed class SignInEvent {
        data class OnSignInSuccess(val messageString: String) : SignInEvent()
        object HideKeyboard : SignInEvent()
        data class ShowSnackBar(val message: Int, val messageString: String?) : SignInEvent()
    }

}