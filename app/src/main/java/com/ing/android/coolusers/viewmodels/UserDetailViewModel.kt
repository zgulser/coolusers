package com.ing.android.coolusers.viewmodels

import android.app.Application
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ing.android.coolusers.domain.listeners.GetUserListener
import com.ing.android.coolusers.domain.dto.User
import com.ing.android.coolusers.view.EVENT_USER_LOAD_FAILED
import java.util.*

internal class UserDetailViewModel constructor(application: Application, private val userId: String): BaseViewModel(application) {

    private val user by lazy {
        MutableLiveData<User>()
    }

    init {
        loadUser()
    }

    // also can be called on a user-action by making it public
    private fun loadUser() =
            userInteractor.getUser(userId, HashMap(), UserListener())

    fun getUser() : LiveData<User> = user

    private inner class UserListener : GetUserListener {

        override fun onSuccess(result: User) {
            user.postValue(result)
        }

        override fun onFailure(reason: String) {
            val intent = Intent()
            intent.action = EVENT_USER_LOAD_FAILED
            LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(intent)
        }
    }
}
