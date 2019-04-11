package com.ing.android.coolusers.providers.data.gateways.local

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ing.android.coolusers.coroutine.AppScope
import com.ing.android.coolusers.domain.listeners.*
import com.ing.android.coolusers.domain.dto.User
import com.ing.android.coolusers.domain.usecases.CommandGateway
import com.ing.android.coolusers.domain.usecases.USER_ID_BUNDLE_KEY
import com.ing.android.coolusers.utilities.InjectionUtils
import com.ing.android.coolusers.utilities.ServiceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException

/**
 * Desc: An implementation of CommandGateway. Loads one User from a local json file
 */

class GetUserAssetsCommandGateway(context: Context) : CommandGateway(context) {

    init {
        dataParser = InjectionUtils.provideDataParser()
    }

    override fun transact(arguments: Bundle, genericListener: GenericListener) {
        AppScope.launch(Dispatchers.IO) {
            val uid: String = arguments.getString(USER_ID_BUNDLE_KEY)
            val fileUrl = ServiceUtils.buildGetUserUrl(uid)
            val userAsStr = context.assets.open(fileUrl + ".json").bufferedReader().use { it.readText() }
            val userResult = dataParser.parseUser(userAsStr, ::parsePayload)
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