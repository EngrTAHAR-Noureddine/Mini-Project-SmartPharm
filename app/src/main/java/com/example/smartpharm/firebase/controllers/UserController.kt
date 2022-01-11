package com.example.smartpharm.firebase.controllers



import androidx.fragment.app.FragmentActivity
import com.example.smartpharm.firebase.controllers.users.ClientController
import com.example.smartpharm.firebase.controllers.users.LogInController


object UserController {

    fun loginUser(email:String,password:String,context: FragmentActivity) {
        LogInController.loginUser(email,password,context)
    }

    fun getPharmacies(context: FragmentActivity){
        ClientController.getListPharmacies(context)
    }



}