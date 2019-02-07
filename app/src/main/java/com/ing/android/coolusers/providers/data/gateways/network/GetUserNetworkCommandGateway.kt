package com.ing.android.coolusers.providers.data.gateways.network

import android.content.Context
import android.os.Bundle
import com.ing.android.coolusers.domain.listeners.GenericListener
import com.ing.android.coolusers.domain.usecases.CommandGateway

/**
 * Desc: Showcase. Left unimplemented on purpose.
 *
 * Class to operate on remote/network resources.
 */

class GetUserNetworkCommandGateway (context: Context) : CommandGateway(context) {
    override fun transact(arguments: Bundle, genericListener: GenericListener) {
        TODO("i.e.: Plug a retrofit implementation")
    }
}