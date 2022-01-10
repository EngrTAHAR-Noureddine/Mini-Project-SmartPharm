package com.example.smartpharm.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "users_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("userId") var userId: Long = 0L,

    @ColumnInfo(name = "typeUser")
    @SerializedName("typeUser") var typeUser: String = "",

    @ColumnInfo(name = "passwordUser")
    @SerializedName("passwordUser") var passwordUser: String = "",

    @ColumnInfo(name = "name")
    @SerializedName("name") var name: String = "",

    @ColumnInfo(name = "locationUser")
    @SerializedName("locationUser") var locationUser: String = "",

    @ColumnInfo(name = "phoneNumber")
    @SerializedName("phoneNumber") var phoneNumber: String = "",

    @ColumnInfo(name = "emailUser")
    @SerializedName("emailUser") var emailUser: String = "",

    @ColumnInfo(name = "facebookAccount")
    @SerializedName("facebookAccount")var facebookAccount: String = "",

    @ColumnInfo(name = "instagramAccount")
    @SerializedName("instagramAccount") var instagramAccount: String = "",

    @ColumnInfo(name = "photoUser")
    @SerializedName("photoUser") var photoUser: Bitmap =Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888),

    )