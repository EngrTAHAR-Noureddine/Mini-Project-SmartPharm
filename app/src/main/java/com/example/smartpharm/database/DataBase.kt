package com.example.smartpharm.database

import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore

object DataBase {
    @SuppressLint("StaticFieldLeak")
    val db = FirebaseFirestore.getInstance()
}