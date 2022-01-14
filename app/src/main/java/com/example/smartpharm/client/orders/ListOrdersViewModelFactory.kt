package com.example.smartpharm.client.orders

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartpharm.databinding.ListOrdersFragmentBinding
import com.example.smartpharm.firebase.models.User


class ListOrdersViewModelFactory(private val binding: ListOrdersFragmentBinding,private val context : FragmentActivity,private val user: User?):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListOrdersViewModel::class.java)) {
            return ListOrdersViewModel( binding ,context ,user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}