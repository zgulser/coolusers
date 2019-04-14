package com.ing.android.coolusers.service

import com.ing.android.coolusers.domain.listeners.GenericListener
import com.ing.android.coolusers.domain.dto.User

/**
 * Desc: Services (use cases) available for "User"
 */

interface UserService {
    fun getUser(uid: String, queryParams: Map<String, String>, genericListener: GenericListener)

    fun createUser(user: User, queryParams: Map<String, String>, genericListener: GenericListener)

    fun deleteUser(uid: String, queryParams: Map<String, String>, genericListener: GenericListener)

    fun modifyUser(user: User, queryParams: Map<String, String>, genericListener: GenericListener)

    fun getUserList(queryParams: Map<String, String>, genericListener: GenericListener)
}