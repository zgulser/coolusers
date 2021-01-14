package com.ing.android.coolusers.interactor

import android.content.Context
import android.os.Looper
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ing.android.coolusers.coroutine.AppScope
import com.ing.android.coolusers.domain.dto.User
import com.ing.android.coolusers.domain.listeners.GenericListener
import com.ing.android.coolusers.domain.listeners.GetUserListListener
import com.ing.android.coolusers.domain.listeners.GetUserListener
import com.ing.android.coolusers.persistency.UserDatabase
import com.ing.android.coolusers.utilities.InjectionUtils

class UserInteractor(private val context: Context){

    private val userService by lazy {
        InjectionUtils.provideUserService(context)
    }

    private val userDao by lazy {
        UserDatabase.getInstance(context).userDao()
    }

    fun getUser(uid: String, queryParams: Map<String, String>, genericListener: GenericListener): LiveData<User> {
        refreshUser(uid, queryParams, genericListener)
        return userDao.getUser(uid)
    }

    @SuppressWarnings("not used")
    fun loadUser(uid: String, queryParams: Map<String, String>, genericListener: GenericListener): User {
        return userDao.loadUser(uid)
    }

    private fun refreshUser(uid: String, queryParams: Map<String, String>, genericListener: GenericListener){
        userService.getUser(uid, queryParams, object : GetUserListener {
            override fun onSuccess(result: User) {
                userDao.insertUser(result)
                (genericListener as GetUserListener).onSuccess(result)
            }

            override fun onFailure(reason: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    fun getUserList(queryParams: Map<String, String>, genericListener: GenericListener) : LiveData<List<User>>{
        refreshUserList(queryParams, genericListener)
        return userDao.getUserList()
    }

    private fun refreshUserList(queryParams: Map<String, String>, genericListener: GenericListener) {
        // TODO("Add fresh-timeout")
        userService.getUserList(queryParams, object : GetUserListListener {
            override fun onSuccess(result: List<User>) {
                userDao.insertUsers(result)
            }

            override fun onFailure(reason: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    @SuppressWarnings("not used")
    fun modifyUser(user: User, queryParams: Map<String, String>, genericListener: GenericListener){
        userService.modifyUser(user, queryParams, genericListener)
    }
}