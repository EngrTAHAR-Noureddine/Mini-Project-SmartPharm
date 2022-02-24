package com.example.smartpharm.viewmodels

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartpharm.databinding.PharmacistHomeFragmentBinding
import com.example.smartpharm.controllers.OrderController.getOrderOf
import com.example.smartpharm.controllers.OrderController.listOrders
import com.example.smartpharm.models.Order
import com.example.smartpharm.models.User

class PharmacistHomeViewModel(private val binding: PharmacistHomeFragmentBinding
                              ,
                              @SuppressLint("StaticFieldLeak") private val context : FragmentActivity,
                              user: User?
) : ViewModel() {
    val listPharmacyOrders : MutableLiveData<MutableList<Order>?>
        get() = listOrders

    init{
        getOrderOf(user, context)
    }
}