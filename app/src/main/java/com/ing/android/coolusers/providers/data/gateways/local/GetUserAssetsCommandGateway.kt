package com.ing.android.coolusers.providers.data.gateways.local

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ing.android.coolusers.coroutine.AppScope
import com.ing.android.coolusers.domain.listeners.*
import com.ing.android.coolusers.domain.objects.User
import com.ing.android.coolusers.domain.usecases.CommandGateway
import com.ing.android.coolusers.domain.usecases.USER_ID_BUNDLE_KEY
import com.ing.android.coolusers.utilities.ServiceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException

/**
 * Desc: Gateway to a user retriaval service
 */

class GetUserAssetsCommandGateway(context: Context) : CommandGateway(context) {

    override fun transact(requestArgs: Bundle, genericListener: GenericListener) {
        AppScope.launch(Dispatchers.IO) {
            val uid: String = requestArgs.getString(USER_ID_BUNDLE_KEY)
            val fileUrl = ServiceUtils.buildGetUserUrl(uid)
            val userListAsStr = context.assets.open(fileUrl + ".json").bufferedReader().use { it.readText() }
            val userResult = dataParser.parseUser(userListAsStr, ::parsePayload)
            when (userResult) {
                is UserResult.UserSuccess -> (genericListener as GetUserListener).onSuccess(userResult.user)
                is UserResult.UserFailure -> (genericListener as GetUserListener).onFailure(userResult.reason)
            }
        }
    }

    private fun parsePayload(payload: String): UserResult {
        return try {
            val listType = object : TypeToken<User>() {}.type
            UserResult.UserSuccess(Gson().fromJson(payload, listType))
        } catch (exception: JSONException) {
            Log.i(TAG, exception.localizedMessage)
            UserResult.UserFailure(exception.localizedMessage)
        }
    }

    companion object {
        val TAG = GetUserAssetsCommandGateway::javaClass.name
    }
}