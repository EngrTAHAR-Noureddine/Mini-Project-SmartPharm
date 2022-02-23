package com.example.smartpharm.viewmodels


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.smartpharm.R
import com.example.smartpharm.controllers.FileController
import com.example.smartpharm.databinding.PharmacistDetailFragmentBinding
import com.example.smartpharm.models.User

class PharmacistDetailViewModel(private val binding: PharmacistDetailFragmentBinding,
                                private val context : FragmentActivity,
                                private val user: User?
) : ViewModel() {
    fun callPharmacy(){
        if(user != null){
            val tel = user.phoneNumber
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

    fun takeToMap(){
        if(user != null){
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

    fun goToOrder(){
            FileController.emptyDir(context)
            context.findNavController(R.id.myNavHostFragment).navigate(R.id.to_Client_Order)
        }

}