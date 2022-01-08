package com.example.smartpharm.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "orders_table")
data class Orders(
    @PrimaryKey(autoGenerate = true)
    var orderId: Long = 0L,

    @ColumnInfo(name = "idClient")
    var idClient: Long = 0L,

    @ColumnInfo(name = "nameClient")
    var nameClient: String = "",

    @ColumnInfo(name = "idPharmacy")
    var idPharmacy: Long = 0L,

    @ColumnInfo(name = "namePharmacy")
    var namePharmacy: String = "",

    @ColumnInfo(name = "locationClient")
    var locationClient: String = "",

    @ColumnInfo(name = "locationPharmacy")
    var locationPharmacy: String = "",

    @ColumnInfo(name = "status")
    var status: String = "",

    @ColumnInfo(name = "noteOrder")
    var noteOrder: String = "",

    @ColumnInfo(name = "firstPhoto")
    val firstPhoto: Bitmap,

    @ColumnInfo(name = "secondPhoto")
    val secondPhoto: Bitmap,
)
