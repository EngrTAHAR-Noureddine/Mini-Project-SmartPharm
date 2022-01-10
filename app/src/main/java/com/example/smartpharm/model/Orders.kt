package com.example.smartpharm.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "orders_table")
data class Orders(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("orderId") var orderId: Long = 0L,

    @ColumnInfo(name = "idClient")
    @SerializedName("idClient") var idClient: Long = 0L,

    @ColumnInfo(name = "nameClient")
    @SerializedName("nameClient") var nameClient: String = "",

    @ColumnInfo(name = "idPharmacy")
    @SerializedName("idPharmacy") var idPharmacy: Long = 0L,

    @ColumnInfo(name = "namePharmacy")
    @SerializedName("namePharmacy") var namePharmacy: String = "",

    @ColumnInfo(name = "locationClient")
    @SerializedName("locationClient") var locationClient: String = "",

    @ColumnInfo(name = "locationPharmacy")
    @SerializedName("locationPharmacy") var locationPharmacy: String = "",

    @ColumnInfo(name = "status")
    @SerializedName("status") var status: String = "",

    @ColumnInfo(name = "noteOrder")
    @SerializedName("noteOrder") var noteOrder: String = "",

    @ColumnInfo(name = "firstPhoto")
    @SerializedName("firstPhoto") val firstPhoto: Bitmap,

    @ColumnInfo(name = "secondPhoto")
    @SerializedName("secondPhoto") val secondPhoto: Bitmap,
)
