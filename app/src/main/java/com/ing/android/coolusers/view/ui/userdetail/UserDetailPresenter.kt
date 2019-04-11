package com.ing.android.coolusers.view.ui.userdetail

import android.content.Context
import com.ing.android.coolusers.R
import com.ing.android.coolusers.domain.dto.User
import viewmodels.UserDetailViewModel
import java.lang.StringBuilder

class UserDetailPresenter constructor(val context: Context, val uservm: UserDetailViewModel) {

    fun getName() =
            context.resources.getString(R.string.user_name_presenter).format(uservm.getUser().value?.name)

    fun getPhone() =
            context.resources.getString(R.string.user_phone_presenter).format(uservm.getUser().value?.phone)

    fun getEmail() =
            context.resources.getString(R.string.user_email_presenter).format(uservm.getUser().value?.email)

    fun getGroups() =
            context.resources.getString(R.string.user_groups_presenter).format(groupsAsStr(uservm.getUser().value))

    private fun groupsAsStr(user: User?) : StringBuilder {
        val group = StringBuilder()
        for ((index, value) in user?.groups?.iterator()!!.withIndex()) {
            group.append(if (index == 0) value else ", " + value)
        }
        return group
    }
}