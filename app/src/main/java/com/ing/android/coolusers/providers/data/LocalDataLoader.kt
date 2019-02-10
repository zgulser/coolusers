package com.ing.android.coolusers.providers.data

import android.content.Context
import com.ing.android.coolusers.domain.listeners.GenericListener
import com.ing.android.coolusers.domain.usecases.GetUser
import com.ing.android.coolusers.domain.usecases.GetUserList
import com.ing.android.coolusers.providers.data.gateways.local.GetUserAssetsCommandGateway
import com.ing.android.coolusers.providers.data.gateways.local.GetUserListAssetsCommandGateway
import com.ing.android.coolusers.service.DataLoader

/**
 * Desc: An implementation of DataLoader
 */

class LocalDataLoader (val context: Context) : DataLoader {

    override fun getUser(uid: String, queryParams: Map<String, String>, genericListener: GenericListener) =
        GetUser(uid, queryParams, GetUserAssetsCommandGateway(context)).execute(genericListener)

    override fun getUserList(queryParams: Map<String, String>, genericListener: GenericListener) =
        GetUserList(queryParams, GetUserListAssetsCommandGateway(context)).execute(genericListener)

}