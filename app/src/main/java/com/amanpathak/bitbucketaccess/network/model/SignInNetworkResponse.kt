package com.amanpathak.bitbucketaccess.network.model

import com.amanpathak.bitbucketaccess.network.JsonKeys
import com.google.gson.annotations.SerializedName
import org.json.JSONObject


data class SignInNetworkResponse(

    @SerializedName(JsonKeys.LINKS)
    val links: LinkObject?,

    @SerializedName(JsonKeys.DISPLAY_NAME)
    val displayName: String,

    @SerializedName(JsonKeys.USERNAME)
    val userName: String,

    @SerializedName(JsonKeys.ACCOUNT_STATUS)
    val accountStatus: String,

    @SerializedName(JsonKeys.CREATED_ON)
    val createdOn: String
)

data class LinkObject(
    @SerializedName(JsonKeys.AVATAR)
    val avatar: AvatarObject?,
)

data class AvatarObject(
    @SerializedName(JsonKeys.HREF)
    val avatarLink: String?,
)

