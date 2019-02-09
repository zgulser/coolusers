package com.ing.android.coolusers.utilities

const val SERVICE_NAME = "user-service"
const val VERSION = "v2"
const val USERLIST_ENDPOINT = "users"

/**
 * Desc: Static methods used to help creating endpoint urls
 */

object ServiceUtils {
    fun buildGetUserListUrl() = "$SERVICE_NAME/$VERSION/$USERLIST_ENDPOINT"
    fun buildGetUserUrl(uid: String) = "$SERVICE_NAME/$VERSION/$USERLIST_ENDPOINT/$uid"
}