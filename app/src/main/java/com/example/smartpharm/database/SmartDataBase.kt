package com.example.smartpharm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.smartpharm.database.medications.MedicationsDao
import com.example.smartpharm.database.orders.OrdersDao
import com.example.smartpharm.database.users.UsersDao
import com.example.smartpharm.model.Medication
import com.example.smartpharm.model.Orders
import com.example.smartpharm.model.User

@Database(entities = [User::class,Orders::class,Medication::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class smartDataBase: RoomDatabase() {

    abstract fun UsersDao() : UsersDao
    abstract fun OrdersDao() : OrdersDao
    abstract fun MedicationsDao() : MedicationsDao

    companion object {
        private var INSTANCE: smartDataBase? = null

        fun getInstance(context: Context): smartDataBase? {

            if (INSTANCE == null) {
                synchronized(smartDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        smartDataBase::class.java, "smartPharm.db")
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