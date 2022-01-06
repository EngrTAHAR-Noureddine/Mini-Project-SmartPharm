package com.example.smartpharm.database.users

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smartpharm.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UsersDataBase: RoomDatabase() {

    abstract fun UsersDao() : UsersDao

    companion object {
        private var INSTANCE: UsersDataBase? = null

        fun getInstance(context: Context): UsersDataBase? {

            if (INSTANCE == null) {
                synchronized(UsersDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UsersDataBase::class.java, "Users.db")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}