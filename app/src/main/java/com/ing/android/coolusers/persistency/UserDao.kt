package com.ing.android.coolusers.persistency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.ing.android.coolusers.domain.dto.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUser(userId: String) : LiveData<User>

    @Query("SELECT * FROM users WHERE id = :userId")
    fun loadUser(userId: String) : User

    @Query("SELECT * FROM users ORDER BY name")
    fun getUserList() : LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<User>)
}
