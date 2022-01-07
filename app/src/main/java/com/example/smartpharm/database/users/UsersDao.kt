package com.example.smartpharm.database.users

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.smartpharm.model.User


@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * FROM users_table ORDER BY userId ASC")
    fun getAllUsers(): LiveData<List<User>>

    @Query("DELETE FROM users_table")
    fun clear()

    @Update
    fun updateUser(user: User)

}
