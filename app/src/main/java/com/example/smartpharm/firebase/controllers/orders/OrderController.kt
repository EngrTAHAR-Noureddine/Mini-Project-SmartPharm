package com.example.smartpharm.firebase.controllers.orders

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.smartpharm.firebase.DataBase.db
import com.example.smartpharm.firebase.models.Order
import com.example.smartpharm.firebase.models.User
import com.google.firebase.database.DatabaseReference
import java.util.*
import kotlin.collections.ArrayList

object OrderController {

    var listOrders = MutableLiveData<List<Order>>()

    lateinit var dataBaseRef : DatabaseReference

    fun createOrder(
        user: User,
        pharmacy: User,
        note: String,
        files: ArrayList<String>,
        state: String
    ): Order {

        val mapPharmacy: Map<String, String> = mapOf( "namePharmacy" to pharmacy.company,
                                                    "locationPharmacy" to pharmacy.locationUser,
                                                    "photoPharmacy" to pharmacy.photoUser ,
                                                    "phonePharmacy" to pharmacy.phoneNumber
                                                    )
        val mapUser: Map<String, String> = mapOf(   "nameUser" to user.name,
                                                    "locationUser" to user.locationUser,
                                                    "photoUser" to user.photoUser ,
                                                    "phoneUser" to user.phoneNumber
                                                )

        return Order(
            note = note,
            pharmacy = mapPharmacy,
            user = mapUser,
            state = state,
            photoOrders = files,
            pharmacyEmail = pharmacy.emailUser,
            userEmail = user.emailUser
        )

    }


    fun postOrder(order:Order,context:Context){
        db.collection("Order").document(UUID.randomUUID().toString())
            .set(order)
            .addOnSuccessListener { Toast.makeText(context, "Success Upload", Toast.LENGTH_SHORT).show() }
            .addOnFailureListener { Toast.makeText(context, "Failed Upload", Toast.LENGTH_SHORT).show()}
    }


}