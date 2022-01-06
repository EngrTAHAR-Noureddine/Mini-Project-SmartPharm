package com.example.smartpharm.database.users

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.smartpharm.model.User


@Dao
interface UsersDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("DELETE FROM users_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM users_table ORDER BY userId ASC")
    fun getAllUsers(): LiveData<List<User>>

    @Update
    suspend fun updateUser(user: User)

}
