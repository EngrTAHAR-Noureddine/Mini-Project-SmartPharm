package com.example.smartpharm.pharmacist.home

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartpharm.databinding.PharmacistHomeFragmentBinding
import com.example.smartpharm.firebase.controllers.orders.OrderController.getOrderOf
import com.example.smartpharm.firebase.controllers.orders.OrderController.listOrders
import com.example.smartpharm.firebase.models.Order
import com.example.smartpharm.firebase.models.User

class PharmacistHomeViewModel(private val binding: PharmacistHomeFragmentBinding
                              ,private val context : FragmentActivity,
                              private val user: User?
) : ViewModel() {
    val listPharmacyOrders : MutableLiveData<MutableList<Order>?>
        get() = listOrders

    init{
        getOrderOf(user, context)
    }
}