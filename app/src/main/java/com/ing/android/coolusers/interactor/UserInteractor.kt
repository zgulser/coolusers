package com.ing.android.coolusers.interactor

import android.content.Context
import com.ing.android.coolusers.domain.dto.User
import com.ing.android.coolusers.domain.listeners.GenericListener
import com.ing.android.coolusers.persistency.UserDatabase
import com.ing.android.coolusers.utilities.InjectionUtils

class UserInteractor (private val context: Context){

    private val userService by lazy {
        InjectionUtils.provideUserService(context)
    }

    val userDao by lazy {
        UserDatabase.getInstance(context).userDao()
    }

    fun getUser(uid: String, queryParams: Map<String, String>, genericListener: GenericListener){
        userService.getUser(uid, queryParams, genericListener)
    }

    fun modifyUser(user: User, queryParams: Map<String, String>, genericListener: GenericListener){
        userService.modifyUser(user, queryParams, genericListener)
    }

    fun getUserList(queryParams: Map<String, String>, genericListener: GenericListener) {
        userService.getUserList(queryParams, genericListener)
    }
}