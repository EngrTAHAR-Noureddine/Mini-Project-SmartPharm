package com.example.smartpharm.viewmodel_factories

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartpharm.databinding.ClientHomeFragmentBinding
import com.example.smartpharm.viewmodels.ClientHomeViewModel

class ClientHomeViewModelFactory (private val binding: ClientHomeFragmentBinding
                                  ,private val context : FragmentActivity
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientHomeViewModel::class.java)) {
            return ClientHomeViewModel( binding ,context ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}