package com.ing.android.coolusers.domain.dto

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class User(@PrimaryKey val id: String,
                var name: String,
                val phone: String,
                val email: String,
                val age: Int,
                val groups: Array<Group>,
                val imageThumbnailUrl: String,
                val imageUrl: String) : Parcelable {

    override fun equals(other: Any?): Boolean {

        if (other == null || other !is User) {
            return false
        }

        val equal = this.name == other.name &&
                this.id == other.id &&
                this.phone == other.phone &&
                this.email == other.email &&
                this.age == other.age &&
                (this.groups.size == other.groups.size)

        if (equal) {
            var counter = 0
            for (group in this.groups) {
                (group == other.groups[counter++])
            }
        }

        return equal
    }

    override fun hashCode(): Int {

        var hashcode = this.id.hashCode() +
                this.name.hashCode() +
                this.phone.hashCode() +
                this.email.hashCode() +
                this.age.hashCode()

        for (group in this.groups) {
            hashcode += group.hashCode()
        }

        return hashCode() + 31
    }
}

enum class Group {
    Admin,
    User,
    Moderator
}
