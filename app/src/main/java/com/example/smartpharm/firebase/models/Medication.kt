package com.example.smartpharm.firebase.models

import com.google.gson.annotations.SerializedName

data class Medication(
    @SerializedName("emailPharmacy") var emailPharmacy: String = "",
    @SerializedName("nameMedication") var nameMedication: String = ""
)
