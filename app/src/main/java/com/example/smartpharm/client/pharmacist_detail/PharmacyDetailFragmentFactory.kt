package com.example.smartpharm.client.pharmacist_detail

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartpharm.database.users.UsersDao
import com.example.smartpharm.databinding.PharmacistDetailFragmentBinding

class PharmacyDetailFragmentFactory(private val pharmacyDatabase: UsersDao, private val binding: PharmacistDetailFragmentBinding
                                    , private val context : FragmentActivity
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PharmacistDetailViewModel::class.java)) {
            return PharmacistDetailViewModel(pharmacyDatabase, binding ,context ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}