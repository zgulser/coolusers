package com.ing.android.coolusers.domain.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var name: String,
                val id: String,
                val phone: String,
                val email: String,
                val age: Int,
                val groups: Array<Group>,
                val imageThumbnailUrl: String,
                val imageUrl: String) : Parcelable

enum class Group {
    Admin,
    User,
    Moderator
}
