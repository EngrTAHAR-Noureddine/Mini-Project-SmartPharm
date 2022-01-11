package com.example.smartpharm.FireBase.controllers

import android.content.ContentValues.TAG
import android.util.Log
import com.example.smartpharm.FireBase.Converter.toDataClass
import com.example.smartpharm.FireBase.models.User
import com.google.firebase.firestore.FirebaseFirestore


object UserController {

    private val db = FirebaseFirestore.getInstance()

    private var userItemCollection : User? = null

    fun getUserCollection():User?{ return userItemCollection}

    fun loginUser(email:String,password:String) :User? {
        
         var privateUser:User? =null
        
        db.collection("User")
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach { document ->
                    run {

                        val userItem: User = document.data.toDataClass()
                        if (email == userItem.emailUser && password == userItem.passwordUser) {
                            userItemCollection = userItem
                            privateUser = userItem
                        }

                        Log.d("FIRESTORE", "Read Documents ${userItemCollection?.emailUser.toString()}")
                    }

                }
            }
            .addOnFailureListener { exception ->
                Log.w("FIRESTORE", "Error getting documents $exception")
            }
        Log.d("FIRESTORE", "Finally privateUser is : ${userItemCollection?.emailUser.toString()}")

        return privateUser
    }
    
}