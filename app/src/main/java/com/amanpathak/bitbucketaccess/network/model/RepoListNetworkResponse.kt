package com.amanpathak.bitbucketaccess.network.model

import androidx.annotation.Keep
import com.amanpathak.bitbucketaccess.network.JsonKeys
import com.google.gson.annotations.SerializedName


data class RepoListNetworkResponse(
@SerializedName(JsonKeys.REPO_LIST)
val values: List<RepoListItem>
)


data class RepoListItem(
    @SerializedName(JsonKeys.REPO_ID)
    val id : String,
    @SerializedName(JsonKeys.NAME)
    val name : String,
    @SerializedName(JsonKeys.REPO_DESC)
    val desc : String,
    @SerializedName(JsonKeys.LINKS)
    val links : LinkObject,
    @SerializedName(JsonKeys.IS_PRIVATE)
    val isPrivate : Boolean,
    @SerializedName(JsonKeys.CREATED_ON)
    val createdOn : String,
    @SerializedName(JsonKeys.WORK_SPACE_NAME)
    val workspace : WorkSpaceObject
)


data class WorkSpaceObject(
    @SerializedName(JsonKeys.NAME)
    val name : String,
)


