package com.example.smartpharm.models

import com.google.gson.annotations.SerializedName





data class User(
    @SerializedName("idUser") var idUser: String = "",
    @SerializedName("emailUser") var emailUser: String = "",
    @SerializedName("facebookAccount") var facebookAccount: String = "",
    @SerializedName("instagramAccount") var instagramAccount: String = "",
    @SerializedName("locationUser") var locationUser: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("passwordUser") var passwordUser: String = "",
    @SerializedName("phoneNumber") var phoneNumber: String = "",
    @SerializedName("photoUser") var photoUser: String = "",
    @SerializedName("typeUser") var typeUser: String = "",
    @SerializedName("company") var company: String = ""
    )
