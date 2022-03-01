package com.example.smartpharm.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "medication_table")
data class MedicationDB(
    @ColumnInfo(name = "idMedication") @PrimaryKey(autoGenerate = false) var idMedication: String = "",
    @ColumnInfo(name = "idUser") var idUser: String = "",
    @ColumnInfo(name = "Days") var Days: Int = 0,
    @ColumnInfo(name = "DinnerHour")  var DinnerHour: Int = 0,
    @ColumnInfo(name = "DinnerMinute")  var DinnerMinute: Int = 0,
    @ColumnInfo(name = "LaunchHour")  var LaunchHour: Int = 0,
    @ColumnInfo(name = "LaunchMinute")  var LaunchMinute: Int = 0,
    @ColumnInfo(name = "Name") var Name: String="",
    @ColumnInfo(name = "description") var description: String="",
    @ColumnInfo(name = "first_day_Year") var first_day_Year: Int,
    @ColumnInfo(name = "first_day_Month") var first_day_Month: Int,
    @ColumnInfo(name = "first_day_Day") var first_day_Day: Int,
    @ColumnInfo(name = "photo") var photo: String=""

)

