package com.ing.android.coolusers.persistency

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.ing.android.coolusers.domain.dto.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUserList() : LiveData<User>

    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUser(userId: String) : LiveData<User>

    @Update(onConflict =  REPLACE)
    fun updateUser(user: User) : LiveData<User>
}
