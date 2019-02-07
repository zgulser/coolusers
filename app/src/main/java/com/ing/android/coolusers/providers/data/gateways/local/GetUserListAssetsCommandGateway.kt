package com.ing.android.coolusers.providers.data.gateways.local

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.ing.android.coolusers.app.CoolUsersApplication
import com.ing.android.coolusers.coroutine.AppScope
import com.ing.android.coolusers.domain.objects.User
import com.ing.android.coolusers.domain.usecases.CommandGateway
import com.ing.android.coolusers.utilities.ServiceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.gson.reflect.TypeToken
import com.ing.android.coolusers.domain.listeners.*
import org.json.JSONException
import java.lang.Exception

/**
 * Desc: Gateway to a user list retriaval service
 */

class GetUserListAssetsCommandGateway (context: Context) : CommandGateway(context) {

    override fun transact(requestArgs: Bundle, genericListener: GenericListener) {
        AppScope.launch(Dispatchers.IO){
            try {
                val fileUrl = ServiceUtils.buildGetUserListUrl()
                val userListAsStr = context.assets.open(fileUrl + ".json").bufferedReader().use { it.readText() }
                val userResult = dataParser.parseUserList(userListAsStr, ::parsePayload)
                when (userResult) {
                    is UserListResult.UserListSuccess -> (genericListener as GetUserListListener).onSuccess(userResult.userList)
                    is UserListResult.UserListFailure -> (genericListener as GetUserListListener).onFailure(userResult.reason)
                }
            } catch (exception: Exception){
                (genericListener as GetUserListListener).onFailure(exception.localizedMessage)
            }
        }
    }

    private fun parsePayload(payload: String) : UserListResult {
        return try {
            val listType = object : TypeToken<List<User>>() {}.type
            UserListResult.UserListSuccess(Gson().fromJson(payload, listType))
        } catch (exception : JSONException) {
            Log.i(TAG, exception.localizedMessage)
            UserListResult.UserListFailure(exception.localizedMessage)
        }
    }

    companion object {
        val TAG = GetUserListAssetsCommandGateway::javaClass.name
    }
}