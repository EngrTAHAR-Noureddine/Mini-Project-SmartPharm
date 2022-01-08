package com.example.smartpharm.database.medications

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.smartpharm.model.Medication



@Dao
interface MedicationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedication(medication: Medication)

    @Query("SELECT * FROM medications_table ORDER BY medicationId ASC")
    fun getAllMedications(): LiveData<List<Medication>>

    @Query("DELETE FROM medications_table")
    fun clear()

    @Update
    fun updateMedication(medication: Medication)
}