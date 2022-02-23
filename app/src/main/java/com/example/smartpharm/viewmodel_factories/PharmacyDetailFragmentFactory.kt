package com.example.smartpharm.viewmodel_factories

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartpharm.databinding.PharmacistDetailFragmentBinding
import com.example.smartpharm.models.User
import com.example.smartpharm.viewmodels.PharmacistDetailViewModel

class PharmacyDetailFragmentFactory(private val binding: PharmacistDetailFragmentBinding
                                    , private val context : FragmentActivity,private val user: User?
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PharmacistDetailViewModel::class.java)) {
            return PharmacistDetailViewModel( binding ,context , user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}