package com.example.smartpharm.firebase

import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore

object DataBase {
    @SuppressLint("StaticFieldLeak")
    val db = FirebaseFirestore.getInstance()
}