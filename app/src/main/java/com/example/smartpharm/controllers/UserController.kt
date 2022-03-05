package com.example.smartpharm.controllers



import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.smartpharm.controllers.ClientController.updateClient
import com.example.smartpharm.models.User


object UserController {

    fun loginUser(email:String,password:String,context: FragmentActivity) {
        LogInController.loginUser(email,password,context)
    }

    fun getPharmacies(context: Context){
        ClientController.getListPharmacies(context)
    }

    fun updateUser(user: User, change:String, field:String){
        updateClient(user,change, field)
    }



}