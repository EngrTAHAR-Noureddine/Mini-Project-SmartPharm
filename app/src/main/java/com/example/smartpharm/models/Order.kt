package com.example.smartpharm.models

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import com.google.gson.annotations.SerializedName
@IgnoreExtraProperties
data class Order (
    @SerializedName("idOrder") var idOrder: String = "",
    @SerializedName("note") var note: String = "",
    @SerializedName("pharmacy") var pharmacy: Map<String,String>? = null,
    @SerializedName("pharmacyEmail") var pharmacyEmail: String = "",
    @SerializedName("photoOrders") var photoOrders: ArrayList<String>? = null,
    @SerializedName("user") var user: Map<String,String>? = null,
    @SerializedName("userEmail") var userEmail: String = "",//payment
    @SerializedName("payment") var payment: Int = 0,
    @SerializedName("paidOrder") var paidOrder: String = "NON",
    @SerializedName("state") var state: String = ""
)

