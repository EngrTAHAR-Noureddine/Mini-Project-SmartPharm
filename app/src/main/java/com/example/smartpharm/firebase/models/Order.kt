package com.example.smartpharm.firebase.models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
@IgnoreExtraProperties
data class Order (
    @SerializedName("idOrder") var idOrder: String = "",
    @SerializedName("note") var note: String = "",
    @SerializedName("pharmacy") var pharmacy: Map<String,String>? = null,
    @SerializedName("pharmacyEmail") var pharmacyEmail: String = "",
    @SerializedName("photoOrders") var photoOrders: ArrayList<String>? = null,
    @SerializedName("user") var user: Map<String,String>? = null,
    @SerializedName("userEmail") var userEmail: String = "",
    @SerializedName("state") var state: String = ""
)

