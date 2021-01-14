package com.ing.android.coolusers.persistency

import android.content.Context
import com.ing.android.coolusers.domain.dto.User

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ing.android.coolusers.domain.dto.Group

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Group::class)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        @Volatile private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:

        private fun buildDatabase(context: Context): UserDatabase {
            return Room.databaseBuilder(context, UserDatabase::class.java, "user-database").build()
        }
    }
}