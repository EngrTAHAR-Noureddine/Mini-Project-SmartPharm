package com.example.smartpharm.models

import com.google.gson.annotations.SerializedName


data class MyMedications(
    @SerializedName("idMedication") var idMedication: String = "",
    @SerializedName("idUser")  var idUser: String = "",
    @SerializedName("Days")  var Days: Int = 0,
    @SerializedName("Dinner") var Dinner: MutableMap<String,Int>? = null,
    @SerializedName("Launch") var Launch: MutableMap<String,Int>? = null ,
    @SerializedName("Name") var Name: String="",
    @SerializedName("description") var description: String="",
    @SerializedName("first_day") var first_day: MutableMap<String,Int>? = null,
    @SerializedName("photo") var photo: String=""
)

