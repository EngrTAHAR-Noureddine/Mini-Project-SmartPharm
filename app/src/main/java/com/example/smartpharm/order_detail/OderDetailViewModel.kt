package com.example.smartpharm.order_detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.smartpharm.R
import com.example.smartpharm.databinding.OderDetailFragmentBinding
import com.example.smartpharm.firebase.controllers.orders.OrderController.changeStateOrder
import com.example.smartpharm.firebase.controllers.orders.OrderController.deleteOrder
import com.example.smartpharm.firebase.controllers.orders.OrderController.listState
import com.example.smartpharm.firebase.models.Order
import com.example.smartpharm.firebase.notification.NotificationController

class OderDetailViewModel(private val binding: OderDetailFragmentBinding,
                          private val context: FragmentActivity,
                          private val type : String,
                          private val order : Order? ) : ViewModel() {


    fun phoneNumber(){
        if(order != null){
            val tel = if(type == "Pharmacy") order.user!!["phoneUser"] else order.pharmacy!!["phonePharmacy"]
            val url = Uri.parse("tel:$tel")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()))
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    context,
                    "You haven't phone application to make a call",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    fun locationUser(){
        if(order != null){
            val latitude = 28.0339
            val longitude = 1.6596
            val url = Uri.parse("geo:$latitude,$longitude")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()))
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    context,
                    "You haven't an application to find a location",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun acceptOrder(){
       if(order!=null) if(type == "Pharmacy"){
           Log.v("TAG BUTTON", "ACCEPTED PHARMACY")
           changeStateOrder(order,context,2)
           context.findNavController(R.id.myPharmacyNavHostFragment).popBackStack()
       } else{
           deleteOrder(order,context)
           Log.v("TAG BUTTON", "REJECTED")
           context.findNavController(R.id.myNavHostFragment).popBackStack()
       }
    }
    fun rejectOrder(){
        if(order!=null){
            deleteOrder(order,context)
            if(type == "Pharmacy") {
                order.state = listState[1]
                NotificationController.createNotification(order, "Client", context)
                Log.v("TAG BUTTON", "REJECTED PHARMACY")
                context.findNavController(R.id.myPharmacyNavHostFragment).popBackStack()
            }else{
                Log.v("TAG BUTTON", "REJECT USER")
                context.findNavController(R.id.myNavHostFragment).popBackStack()
            }
        }
    }
}