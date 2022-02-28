package com.example.smartpharm.controllers



import android.content.Context
import androidx.fragment.app.FragmentActivity


object UserController {

    fun loginUser(email:String,password:String,context: FragmentActivity) {
        LogInController.loginUser(email,password,context)
    }

    fun getPharmacies(context: Context){
        ClientController.getListPharmacies(context)
    }



}