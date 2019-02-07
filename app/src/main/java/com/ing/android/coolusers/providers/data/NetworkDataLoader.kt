package com.ing.android.coolusers.providers.data

import android.content.Context
import com.ing.android.coolusers.domain.listeners.GenericListener
import com.ing.android.coolusers.domain.usecases.GetUser
import com.ing.android.coolusers.domain.usecases.GetUserList
import com.ing.android.coolusers.providers.data.gateways.network.GetUserNetworkCommandGateway
import com.ing.android.coolusers.providers.data.gateways.network.GetUserListNetworkCommandGateway
import com.ing.android.coolusers.service.DataLoader

/**
 * Desc: Defines what and how the commands (use cases) to be executed upon an incoming User request
 */

class NetworkDataLoader (val context: Context) : DataLoader {

    override fun getUser(uid: String, queryParams: Map<String, String>, genericListener: GenericListener) {
        GetUser(uid, queryParams, GetUserNetworkCommandGateway(context)).execute(genericListener)
    }

    override fun getUserList(queryParams: Map<String, String>, genericListener: GenericListener) {
        GetUserList(queryParams, GetUserListNetworkCommandGateway(context)).execute(genericListener)
    }
}