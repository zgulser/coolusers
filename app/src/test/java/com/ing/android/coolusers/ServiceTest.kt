package com.ing.android.coolusers

import com.ing.android.coolusers.domain.listeners.GetUserListListener
import com.ing.android.coolusers.domain.listeners.GetUserListener
import com.ing.android.coolusers.domain.dto.User
import com.ing.android.coolusers.utilities.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ServiceTest {

    @Mock
    val userService = InjectionUtils.provideUserService(RuntimeEnvironment.application)

    @Test
    fun test_UriBuilders() {
        assert(ServiceUtils.buildGetUserListUrl().equals("$SERVICE_NAME/$VERSION/$USERLIST_ENDPOINT"),
            {"@test_UriBuilders: unable to generate source Uri"})

        assert(ServiceUtils.buildGetUserUrl(SAMPLE_USER_ID).equals("$SERVICE_NAME/$VERSION/$USERLIST_ENDPOINT/$SAMPLE_USER_ID"),
            {"@test_UriBuilders: unable to generate source Uri"})
    }

    @Test
    fun test_GetUser() {
        userService.getUser(
                SAMPLE_USER_ID_1,
                queryParams = HashMap(),
                genericListener = object : GetUserListener {
                    override fun onSuccess(result: User) {
                        assert(result.name.equals("Jenny"), {"@test_GetUserList: unable to get user"})
                    }

                    override fun onFailure(reason: String) {
                        assert(false)
                    }

                })
    }

    @Test
    fun test_GetUserList() {
        userService.getUserList(
                queryParams = HashMap(),
                genericListener = object : GetUserListListener{
                    override fun onSuccess(result: List<User>) {
                        assert(result.size == 16, {"@test_GetUserList: unable to get user list"})
                    }

                    override fun onFailure(reason: String) {
                        assert(false)
                    }
                })

    }

}