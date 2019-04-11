package com.ing.android.coolusers.domain.listeners

import com.ing.android.coolusers.domain.dto.User

sealed class UserResult{
    data class UserSuccess(val user: User): UserResult()
    data class UserFailure(val reason: String): UserResult()
}

sealed class UserListResult{
    data class UserListSuccess(val userList: List<User>): UserListResult()
    data class UserListFailure(val reason: String): UserListResult()
}

interface GenericListener

interface GetUserListener : GenericListener {
    fun onSuccess(result: User)
    fun onFailure(reason: String)
}

interface GetUserListListener : GenericListener {
    fun onSuccess(result: List<User>)
    fun onFailure(reason: String)
}