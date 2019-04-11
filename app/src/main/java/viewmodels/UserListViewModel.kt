package viewmodels

import android.app.Application
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ing.android.coolusers.domain.listeners.GetUserListListener
import com.ing.android.coolusers.domain.dto.User
import com.ing.android.coolusers.view.EVENT_USER_LIST_LOAD_FAILED

class UserListViewModel constructor(application: Application): BaseViewModel(application) {

    private val userList: MutableLiveData<List<User>> = MutableLiveData()

    init {
        loadUserList()
    }

    // also can be called on a user-action by making it public
    private fun loadUserList() =
            userInteractor.getUserList(HashMap(), CustomUserListListener())

    fun getUsers() : MutableLiveData<List<User>>  = userList

    override fun onCleared() {
        super.onCleared()
    }

    private inner class CustomUserListListener : GetUserListListener {

        override fun onSuccess(result: List<User>) {
            userList.postValue(result)

            // stub to test data update
            Thread(object : Runnable{
                override fun run() {
                    Thread.sleep(10000);
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
