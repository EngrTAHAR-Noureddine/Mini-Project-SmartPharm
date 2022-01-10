package com.example.smartpharm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "medications_table")
data class Medication(

    @PrimaryKey(autoGenerate = true)
    @SerializedName("medicationId") var medicationId: Long = 0L,

    @ColumnInfo(name = "idPharmacy")
    @SerializedName("idPharmacy") var idPharmacy: Long = 0L,

    @ColumnInfo(name = "namePharmacy")
    @SerializedName("namePharmacy") var namePharmacy: String = "",

    @ColumnInfo(name = "nameMedication")
    @SerializedName("nameMedication") var nameMedication: String = "",

    )
