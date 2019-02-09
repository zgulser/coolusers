package com.ing.android.coolusers.view.ui.userdetail

import android.app.Application
import android.content.Intent
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ing.android.coolusers.domain.listeners.GetUserListener
import com.ing.android.coolusers.domain.objects.User
import com.ing.android.coolusers.view.EVENT_USER_LOAD_FAILED
import com.ing.android.coolusers.view.ui.BaseViewModel
import java.lang.StringBuilder

class UserDetailViewModel constructor(application: Application): BaseViewModel(application) {

    var user: MutableLiveData<User> = MutableLiveData()

    fun init(@NonNull uid: String) = userService.getUser(uid, HashMap(), UserListener())

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
