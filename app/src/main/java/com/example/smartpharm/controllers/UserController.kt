package com.example.smartpharm.controllers



import androidx.fragment.app.FragmentActivity


object UserController {

    fun loginUser(email:String,password:String,context: FragmentActivity) {
        LogInController.loginUser(email,password,context)
    }

    fun getPharmacies(context: FragmentActivity){
        ClientController.getListPharmacies(context)
    }



}