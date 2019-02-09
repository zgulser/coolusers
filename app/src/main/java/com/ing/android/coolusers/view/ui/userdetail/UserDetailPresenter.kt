package com.ing.android.coolusers.view.ui.userdetail

import android.content.Context
import com.ing.android.coolusers.R
import com.ing.android.coolusers.domain.objects.User
import java.lang.StringBuilder

class UserDetailPresenter constructor(val context: Context, val uservm: UserDetailViewModel) {

    fun getName(): String {
        return context.resources.getString(R.string.user_name_presenter).format(uservm.user.value?.name)
    }

    fun getPhone(): String {
        return context.resources.getString(R.string.user_phone_presenter).format(uservm.user.value?.phone)
    }

    fun getEmail(): String {
        return context.resources.getString(R.string.user_email_presenter).format(uservm.user.value?.email)
    }

    fun getGroups(): String {
        return context.resources.getString(R.string.user_groups_presenter).format(groupsAsStr(uservm.user.value))
    }

    private fun groupsAsStr(user: User?) : StringBuilder {
        val group = StringBuilder()
        for ((index, value) in user?.groups?.iterator()!!.withIndex()) {
            group.append(if (index == 0) value else ", " + value)
        }
        return group
    }
}