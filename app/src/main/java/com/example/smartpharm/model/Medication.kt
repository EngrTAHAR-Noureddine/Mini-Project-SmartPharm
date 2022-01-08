package com.example.smartpharm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medications_table")
data class Medication(

    @PrimaryKey(autoGenerate = true)
    var medicationId: Long = 0L,

    @ColumnInfo(name = "idPharmacy")
    var idPharmacy: Long = 0L,

    @ColumnInfo(name = "namePharmacy")
    var namePharmacy: String = "",

    @ColumnInfo(name = "nameMedication")
    var nameMedication: String = "",

)
