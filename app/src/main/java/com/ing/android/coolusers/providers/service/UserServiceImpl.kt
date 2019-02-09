package com.ing.android.coolusers.providers.service

import android.content.Context
import com.ing.android.coolusers.domain.listeners.GenericListener
import com.ing.android.coolusers.domain.objects.User
import com.ing.android.coolusers.service.DataLoader
import com.ing.android.coolusers.service.UserService
import com.ing.android.coolusers.utilities.InjectionUtils
import com.ing.android.coolusers.utilities.SingletonHolder

/**
 * Desc: Implementation of UserService
 */

class UserServiceImpl private constructor(context: Context) : UserService {

    val dataLoader: DataLoader by lazy {
        InjectionUtils.provideLocalDataLoader(context)
    }

    override fun getUser(uid: String, queryParams: Map<String, String>, genericListener: GenericListener) {
        dataLoader.getUser(uid, queryParams, genericListener)
    }

    override fun createUser(user: User, queryParams: Map<String, String>, genericListener: GenericListener) {
        dataLoader.createUser(user, queryParams, genericListener)
    }

    override fun deleteUser(uid: String, queryParams: Map<String, String>, genericListener: GenericListener) {
        dataLoader.deleteUser(uid, queryParams, genericListener)
    }

    override fun modifyUser(user: User, queryParams: Map<String, String>, genericListener: GenericListener) {
        dataLoader.updateUser(user, queryParams, genericListener)
    }

    override fun getUserList(queryParams: Map<String, String>, genericListener: GenericListener) {
        dataLoader.getUserList(queryParams, genericListener)
    }

    companion object : SingletonHolder<UserServiceImpl, Context>(::UserServiceImpl)

}