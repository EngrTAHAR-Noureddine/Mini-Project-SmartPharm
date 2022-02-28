package com.example.smartpharm.viewmodels


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.smartpharm.R
import com.example.smartpharm.controllers.OrderController.changeStateOrder
import com.example.smartpharm.controllers.OrderController.deleteOrder
import com.example.smartpharm.controllers.OrderController.listState
import com.example.smartpharm.models.Order
import com.example.smartpharm.controllers.NotificationController

class OderDetailViewModel: ViewModel() {

    fun phoneNumber(order: Order? ,type:String, activity: FragmentActivity){
        if(order != null){
            val tel = if(type == "Pharmacy") order.user!!["phoneUser"] else order.pharmacy!!["phonePharmacy"]
            val url = Uri.parse("tel:$tel")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()))
            try {
                activity.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    activity,
                    "You haven't phone application to make a call",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    fun locationUser(order: Order?,activity: FragmentActivity){
        if(order != null){
            val latitude = 28.0339
            val longitude = 1.6596
            val url = Uri.parse("geo:$latitude,$longitude")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()))
            try {
                activity.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    activity,
                    "You haven't an application to find a location",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    fun acceptOrder(order: Order?,type: String,activity: FragmentActivity){
       if(order!=null) if(type == "Pharmacy"){
           Log.v("TAG BUTTON", "ACCEPTED PHARMACY")
           changeStateOrder(order,activity,2)
           activity.findNavController(R.id.myPharmacyNavHostFragment).popBackStack()
       } else{
           deleteOrder(order,activity)
           Log.v("TAG BUTTON", "REJECTED")
           activity.findNavController(R.id.myNavHostFragment).popBackStack()
       }
    }
    fun rejectOrder(order: Order?,type: String,activity: FragmentActivity){
        if(order!=null){
            deleteOrder(order,activity)
            if(type == "Pharmacy") {
                order.state = listState[1]
                NotificationController.createNotification(order, "Client", activity)
                Log.v("TAG BUTTON", "REJECTED PHARMACY")
                activity.findNavController(R.id.myPharmacyNavHostFragment).popBackStack()
            }else{
                Log.v("TAG BUTTON", "REJECT USER")
                activity.findNavController(R.id.myNavHostFragment).popBackStack()
            }
        }
    }
}