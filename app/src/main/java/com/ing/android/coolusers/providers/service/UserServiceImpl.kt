package com.ing.android.coolusers.providers.service

import android.content.Context
import com.ing.android.coolusers.domain.listeners.GenericListener
import com.ing.android.coolusers.domain.dto.User
import com.ing.android.coolusers.service.DataSource
import com.ing.android.coolusers.service.UserService
import com.ing.android.coolusers.utilities.InjectionUtils
import com.ing.android.coolusers.utilities.SingletonHolder

/**
 * Desc: Implementation of UserService
 */

class UserServiceImpl private constructor(context: Context) : UserService {

    val dataSource: DataSource by lazy {
        InjectionUtils.provideLocalDataLoader(context)
    }

    override fun getUser(uid: String, queryParams: Map<String, String>, genericListener: GenericListener) {
        dataSource.getUser(uid, queryParams, genericListener)
    }

    override fun createUser(user: User, queryParams: Map<String, String>, genericListener: GenericListener) {
        dataSource.createUser(user, queryParams, genericListener)
    }

    override fun deleteUser(uid: String, queryParams: Map<String, String>, genericListener: GenericListener) {
        dataSource.deleteUser(uid, queryParams, genericListener)
    }

    override fun modifyUser(user: User, queryParams: Map<String, String>, genericListener: GenericListener) {
        dataSource.updateUser(user, queryParams, genericListener)
    }

    override fun getUserList(queryParams: Map<String, String>, genericListener: GenericListener) {
        dataSource.getUserList(queryParams, genericListener)
    }

    companion object : SingletonHolder<UserServiceImpl, Context>(::UserServiceImpl)

}