package com.ing.android.coolusers.view.ui.userlist

import android.app.Application
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ing.android.coolusers.domain.listeners.GetUserListListener
import com.ing.android.coolusers.domain.objects.User
import com.ing.android.coolusers.view.EVENT_USER_LIST_LOAD_FAILED
import com.ing.android.coolusers.view.ui.BaseViewModel

class UserListViewModel constructor(application: Application): BaseViewModel(application) {

    val userList: MutableLiveData<List<User>> = MutableLiveData()

    fun loadUserList() =
            userService.getUserList(HashMap(), CustomUserListListener())

    private inner class CustomUserListListener : GetUserListListener {

        override fun onSuccess(result: List<User>) {
            userList.postValue(result)
            Thread(object : Runnable{
                override fun run() {
                    Thread.sleep(4000);
                    userList.value!!.get(0)?.name = "abuss"
                    userList.postValue(result)
                }

            }).start()
        }

        override fun onFailure(reason: String) {
            val intent = Intent()
            intent.action = EVENT_USER_LIST_LOAD_FAILED
            LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(intent)
        }
    }
}
