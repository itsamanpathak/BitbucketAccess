package com.amanpathak.bitbucketaccess.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amanpathak.bitbucketaccess.model.SignInModel
import com.amanpathak.bitbucketaccess.utils.SharedPreferenceManager
import com.amanpathak.bitbucketaccess.utils.SharedPreferenceManager.KEY_PROFILE_DETAIL

class SettingViewModel : ViewModel() {

    val profileDetail = MutableLiveData<SignInModel>()

    init {
        profileDetail.value = SharedPreferenceManager.retrieveObject(KEY_PROFILE_DETAIL, SignInModel::class.java)
    }


}