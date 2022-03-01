package com.example.smartpharm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smartpharm.models.MedicationDB

@Database(entities = [MedicationDB::class], version = 1, exportSchema = false)
abstract class OffLineDB : RoomDatabase() {

    abstract fun MedicationDao() : MedicationDao


    companion object {
        private var INSTANCE: OffLineDB? = null

        fun getInstance(context: Context): OffLineDB? {
            if (INSTANCE == null) {
                synchronized(OffLineDB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        OffLineDB::class.java, "smartpharm.db")
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