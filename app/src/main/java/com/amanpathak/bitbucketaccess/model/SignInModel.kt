package com.amanpathak.bitbucketaccess.model

import androidx.annotation.Keep

data class SignInModel(
    val avatar : String? = "",
    val displayName: String? = "",
    val userName: String? = "",
    val accountStatus: String? = "",
    val createdOn: String? = ""
)
