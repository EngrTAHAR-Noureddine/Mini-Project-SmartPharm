package com.example.smartpharm.viewmodels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartpharm.databinding.ListOrdersFragmentBinding
import com.example.smartpharm.controllers.OrderController
import com.example.smartpharm.models.Order
import com.example.smartpharm.models.User

class ListOrdersViewModel(private val binding: ListOrdersFragmentBinding,private val context : FragmentActivity,private val user: User?) : ViewModel() {

    val listUserOrders : MutableLiveData<MutableList<Order>?>
        get() = OrderController.listOrders

    init{
        OrderController.getOrderOf(user, context)
    }
}