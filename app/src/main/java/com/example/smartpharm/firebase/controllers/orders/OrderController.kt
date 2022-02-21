package com.example.smartpharm.firebase.controllers.orders

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.smartpharm.firebase.DataBase.db
import com.example.smartpharm.firebase.models.Order
import com.example.smartpharm.firebase.models.User
import com.example.smartpharm.firebase.notification.NotificationController.createNotification
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*


object OrderController {

    var listOrders = MutableLiveData<MutableList<Order>?>()
    var noteOrder  = MutableLiveData<String?>()

    var listState : List<String> = listOf("En cours","Rejeter","Accepter")

    lateinit var dataBaseRef : DatabaseReference

    fun createOrder(user: User, pharmacy: User, note: String, files: ArrayList<String>, state: String): Order {

        val mapPharmacy: Map<String, String> = mapOf( "namePharmacy" to pharmacy.company,
                                                    "locationPharmacy" to pharmacy.locationUser,
                                                    "photoPharmacy" to pharmacy.photoUser ,
                                                    "idPharmacy" to pharmacy.idUser,
                                                    "phonePharmacy" to pharmacy.phoneNumber
                                                    )
        val mapUser: Map<String, String> = mapOf(   "nameUser" to user.name,
                                                    "locationUser" to user.locationUser,
                                                    "photoUser" to user.photoUser ,
                                                    "idUser" to user.idUser,
                                                    "phoneUser" to user.phoneNumber
                                                )

        return Order(
            idOrder = UUID.randomUUID().toString(),
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
        db.collection("Order").document(order.idOrder)
            .set(order)
            .addOnSuccessListener {

                createNotification(order,"Pharmacy",context)

                Toast.makeText(context, "Success Upload", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { Toast.makeText(context, "Failed Upload", Toast.LENGTH_SHORT).show()}
    }

    fun getOrderOf(user:User?,context: Context){
        db.collection("Order")
            .get()
            .addOnSuccessListener { querySnapshot ->run{
                if (!querySnapshot.isEmpty) {
                    var list:List<Order>? = querySnapshot.toObjects(Order::class.java)
                    listOrders.value =if(user!=null) when(user.typeUser){
                                                                            "Client" -> (list?.filter { item  -> item.userEmail == user.emailUser }) as MutableList<Order>?
                                                                             else -> (list?.filter { item  -> item.pharmacyEmail == user.emailUser }) as MutableList<Order>?
                                                                        } else null
                }else{
                    val text = "The Pharmacy hasn't the order"
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

    fun deleteOrder(order:Order,context: Context){
        val storage = Firebase.storage
        val storageRef = storage.reference
        val photoList = order.photoOrders

        db.collection("Order").document(order.idOrder)
            .delete()
            .addOnSuccessListener {
               Log.d("FIRESTORE", "DocumentSnapshot successfully deleted!")
               Toast.makeText(context, "successfully deleted!", Toast.LENGTH_SHORT).show()
               val list =if(listOrders.value!=null) listOrders.value!!.filter {item:Order-> item.idOrder!= order.idOrder}else null
               listOrders.value = list as MutableList<Order>?

                if(!photoList.isNullOrEmpty()){
                    for (item in photoList){
                        val riversRef = storageRef.child("ImagesSmartPharm/${item}")
                        riversRef.delete().addOnSuccessListener {
                            Log.d("FIRESTORE", "DocumentSnapshot successfully deleted!")
                        }.addOnFailureListener {
                            Log.d("FIRESTORE", "DocumentSnapshot Failed deleted!")
                        }
                    }
                }
            }
            .addOnFailureListener {  Toast.makeText(context, "Delete Failed!", Toast.LENGTH_SHORT).show() }
    }

    fun changeStateOrder(order:Order,context: Context,range:Int){
        db.collection("Order").document(order.idOrder)
            .update("state", listState[range])
            .addOnSuccessListener {
                Log.d("FIRESTORE", "DocumentSnapshot successfully deleted!")
                Toast.makeText(context, "successfully deleted!", Toast.LENGTH_SHORT).show()
                val list : MutableList<Order>? = listOrders.value
                list?.find { item -> item.idOrder == order.idOrder}?.state = listState[range]
                listOrders.value = list
                order.state = listState[range]
                createNotification(order,"Client",context)

            }
            .addOnFailureListener {  Toast.makeText(context, "Delete Failed!", Toast.LENGTH_SHORT).show() }
    }



}