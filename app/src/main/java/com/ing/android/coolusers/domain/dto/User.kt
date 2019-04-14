package com.ing.android.coolusers.domain.dto


import android.os.Parcelable
import android.util.JsonReader
import android.util.JsonWriter
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull
import java.io.IOException
import java.io.StringReader
import java.io.StringWriter

@Entity(tableName = "users")
@Parcelize
data class User(
        @PrimaryKey @ColumnInfo(name = "id") val id: String,
        var name: String, // 'var' for testing
        val phone: String,
        val email: String,
        val age: Int,
        val groups: String,
        val imageThumbnailUrl: String,
        val imageUrl: String) : Parcelable {

    override fun equals(other: Any?): Boolean {

        if (other == null || other !is User) {
            return false
        }

        return (this.name == other.name &&
                this.id == other.id &&
                this.phone == other.phone &&
                this.email == other.email &&
                this.age == other.age &&
                this.groups == other.groups)
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

// NOTE: enum can't be instantiated so can't be used in the entity
enum class Group {
    Admin,
    User,
    Moderator;

    @TypeConverter
    fun toGroup(groupName: String) : Group =
        when (groupName) {
            "Admin" -> Admin
            "User" -> User
            "Moderator" -> Moderator
            else -> User
        }

    @TypeConverter
    fun fromGroup(group: Group) : String =
        when (group) {
            Admin -> "Admin"
            User -> "User"
            Moderator -> "Moderator"
        }

    @TypeConverter
    fun fromGroupSet(strings: MutableList<Group>?): String? {
        if (strings == null) {
            return null
        }

        val result = StringWriter()
        val json = JsonWriter(result)

        try {
            json.beginArray()

            for (s in strings) {
                json.value(fromGroup(s))
            }

            json.endArray()
            json.close()
        } catch (e: IOException) {
            Log.e("Group", "Exception creating JSON", e)
        }

        return result.toString()
    }

    @TypeConverter
    fun toGroupSet(strings: String?): MutableList<Group>? {
        if (strings == null) {
            return null
        }

        val reader = StringReader(strings)
        val json = JsonReader(reader)
        val result = mutableListOf<Group>()

        try {
            json.beginArray()

            while (json.hasNext()) {
                result.add(toGroup(json.nextString()))
            }

            json.endArray()
        } catch (e: IOException) {
            Log.e("Group", "Exception parsing JSON", e)
        }

        return result
    }
}
