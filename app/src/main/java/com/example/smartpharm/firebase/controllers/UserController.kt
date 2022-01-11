package com.example.smartpharm.firebase.controllers


import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity
import com.example.smartpharm.firebase.controllers.users.LogInController
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("StaticFieldLeak")
object UserController {

    val db = FirebaseFirestore.getInstance()

    fun loginUser(email:String,password:String,context: FragmentActivity) {
        LogInController.loginUser(email,password,context)
    }

}