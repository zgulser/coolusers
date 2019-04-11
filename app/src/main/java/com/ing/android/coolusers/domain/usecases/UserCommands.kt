package com.ing.android.coolusers.domain.usecases

import android.os.Bundle
import com.ing.android.coolusers.domain.listeners.GenericListener
import com.ing.android.coolusers.domain.dto.User

/**
 * Desc: Use cases for the user table
 */

class GetUser(private val uid: String,
              private val queryParams: Map<out String, String> = HashMap(),
              private val gateway: CommandGateway) : Command {

    override fun execute(genericListener: GenericListener) {
        val bundle = Bundle()
        bundle.putString(USER_ID_BUNDLE_KEY, uid)
        queryParams.entries.iterator().forEach {
            bundle.putString(it.key, it.value)
        }
        gateway.performTransaction(bundle, this, genericListener)
    }
}

@SuppressWarnings("showcase: not used")
class CreateUser(private val user: User,
                 private val queryParams: Map<out String, String> = HashMap(),
                 private val gateway: CommandGateway) : Command {

    override fun execute(genericListener: GenericListener) {
        val bundle = Bundle()
        queryParams.entries.iterator().forEach {
            bundle.putString(it.key, it.value)
        }
        gateway.performTransaction(Bundle(), this, genericListener)
    }
}

@SuppressWarnings("showcase: not used")
class DeleteUser(private val uid: String,
                 private val queryParams: Map<out String, String> = HashMap(),
                 private val gateway: CommandGateway) : Command {

    override fun execute(genericListener: GenericListener) {
        val bundle : Bundle = Bundle.EMPTY
        bundle.putString(USER_ID_BUNDLE_KEY, uid)
        queryParams.entries.iterator().forEach {
            bundle.putString(it.key, it.value)
        }
        gateway.performTransaction(Bundle(), this, genericListener)
    }
}

@SuppressWarnings("showcase: not used")
class UpdateUser(private val user: User,
                 private val queryParams: Map<out String, String> = HashMap(),
                 private val gateway: CommandGateway) : Command {

    override fun execute(genericListener: GenericListener) {
        val bundle : Bundle = Bundle.EMPTY
        bundle.putParcelable(USER_BUNDLE_KEY, user)
        queryParams.entries.iterator().forEach {
            bundle.putString(it.key, it.value)
        }
        gateway.performTransaction(Bundle(), this, genericListener)
    }
}

class GetUserList(private val queryParams: Map<out String, String> = HashMap(),
                  private val gateway: CommandGateway) : Command {

    override fun execute(genericListener: GenericListener) {
        val bundle = Bundle()
        queryParams.entries.iterator().forEach {
            bundle.putString(it.key, it.value)
        }
        gateway.performTransaction(bundle, this, genericListener)
    }
}