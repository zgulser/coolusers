package com.ing.android.coolusers.service

import com.ing.android.coolusers.domain.listeners.GenericListener
import com.ing.android.coolusers.domain.objects.User

/**
 * Desc: Corresponding loader methods to delegate service requests
 */

interface DataLoader {

    fun getUser(uid: String, queryParams: Map<String, String>, genericListener: GenericListener)

    fun createUser(user: User, queryParams: Map<String, String>, genericListener: GenericListener) =
            println("@DataLoader::createUser:Default implementation")

    fun deleteUser(uid: String, queryParams: Map<String, String>, genericListener: GenericListener) =
            println("@DataLoader::deleteUser:Default implementation")

    fun updateUser(user: User, queryParams: Map<String, String>, genericListener: GenericListener) =
            println("@DataLoader::modifyUser:Default implementation")

    fun getUserList(queryParams: Map<String, String>, genericListener: GenericListener)
}