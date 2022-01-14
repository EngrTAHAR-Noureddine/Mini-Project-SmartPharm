package com.example.smartpharm.pharmacist.home

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartpharm.databinding.PharmacistHomeFragmentBinding
import com.example.smartpharm.firebase.models.User

class PharmacieHomeViewModelFactory(private val binding: PharmacistHomeFragmentBinding
                                    , private val context : FragmentActivity,
                                    private val user: User?
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PharmacistHomeViewModel::class.java)) {
            return PharmacistHomeViewModel( binding ,context ,user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}