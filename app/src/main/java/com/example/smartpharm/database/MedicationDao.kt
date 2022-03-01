package com.example.smartpharm.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.smartpharm.models.MedicationDB

@Dao
interface MedicationDao {
    @Insert
    fun insert(medication: MedicationDB)

    @Update
    fun update(medication: MedicationDB)

    @Query("DELETE FROM medication_table")
    fun clear()

    @Query("SELECT * FROM medication_table ORDER BY idMedication ASC ")
    fun getAllMedications(): LiveData<List<MedicationDB>>

}