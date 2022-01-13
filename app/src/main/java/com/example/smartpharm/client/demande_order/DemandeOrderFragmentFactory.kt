package com.example.smartpharm.client.demande_order

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartpharm.databinding.DemandeOrderFragmentBinding

class DemandeOrderFragmentFactory(private val binding: DemandeOrderFragmentBinding,
                                  private val context : FragmentActivity):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DemandeOrderViewModel::class.java)) {
            return DemandeOrderViewModel( binding ,context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}