package com.ing.android.coolusers.view.ui.userlist

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ing.android.coolusers.app.CoolUsersApplication
import com.ing.android.coolusers.domain.listeners.GenericListener
import com.ing.android.coolusers.domain.listeners.GetUserListListener
import com.ing.android.coolusers.domain.objects.User
import com.ing.android.coolusers.service.UserService
import com.ing.android.coolusers.utilities.InjectionUtils
import com.ing.android.coolusers.view.EVENT_USER_LIST_LOAD_FAILED
import com.ing.android.coolusers.view.EVENT_USER_LOAD_FAILED
import com.ing.android.coolusers.view.ui.BaseViewModel

class UserListViewModel constructor(application: Application): BaseViewModel(application) {

    val userList: MutableLiveData<List<User>> = MutableLiveData()

    fun loadUserList() = userService.getUserList(HashMap(), CustomUserListListener())

    private inner class CustomUserListListener : GetUserListListener {

        override fun onSuccess(result: List<User>) {
            userList.postValue(result)
        }

        override fun onFailure(reason: String) {
            val intent = Intent()
            intent.action = EVENT_USER_LIST_LOAD_FAILED
            LocalBroadcastManager.getInstance(CoolUsersApplication.getInstance()).sendBroadcast(intent)
        }
    }
}
