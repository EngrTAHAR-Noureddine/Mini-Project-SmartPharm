package com.example.smartpharm.client.pharmacist_detail

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.smartpharm.database.users.UsersDao
import com.example.smartpharm.databinding.PharmacistDetailFragmentBinding

class PharmacistDetailViewModel(private val pharmacyDatabase: UsersDao, private val binding: PharmacistDetailFragmentBinding
                                , private val context : FragmentActivity
) : ViewModel() {
    // TODO: Implement the ViewModel
}