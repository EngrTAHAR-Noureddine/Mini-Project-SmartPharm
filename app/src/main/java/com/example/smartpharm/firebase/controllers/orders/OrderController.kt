package com.example.smartpharm.firebase.controllers.orders

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.smartpharm.firebase.DataBase.db
import com.example.smartpharm.firebase.models.Order
import com.example.smartpharm.firebase.models.User
import com.google.firebase.database.DatabaseReference
import java.util.*
import kotlin.collections.ArrayList

object OrderController {

    var listOrders = MutableLiveData<MutableList<Order>?>()

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

    fun getOrderOf(user:User?,context: Context){
        db.collection("Order")
            .get()
            .addOnSuccessListener { querySnapshot ->run{
                if (!querySnapshot.isEmpty) {
                    var list:MutableList<Order>? = querySnapshot.toObjects(Order::class.java)
                    listOrders.value =if(user!=null) when(user.typeUser){
                                                                            "Client" -> (list?.filter { item  -> item.userEmail == user.emailUser }) as MutableList<Order>?
                                                                             else -> (list?.filter { item  -> item.pharmacyEmail == user.emailUser }) as MutableList<Order>?
                                                                        } else null
                }else{
                    val text = "The Pharmacy hasn't the medications"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(context, text, duration)
                    toast.show()
                }
            }
            }
            .addOnFailureListener { exception ->run{
                Log.w("FIRESTORE", "Error getting documents $exception")
                val text = "Out Connexion"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }
            }
    }


}