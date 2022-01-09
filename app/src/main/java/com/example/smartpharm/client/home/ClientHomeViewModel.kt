package com.example.smartpharm.client.home

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartpharm.database.users.UsersDao
import com.example.smartpharm.databinding.ClientHomeFragmentBinding
import kotlinx.coroutines.launch

class ClientHomeViewModel(private val userDatabase: UsersDao, private val binding: ClientHomeFragmentBinding
                          , private val context : FragmentActivity) : ViewModel() {

    var pharmacies = userDatabase.getAllUsers()

    init {
        initializeListPharmacies()
    }

    private fun initializeListPharmacies() {
        viewModelScope.launch {
            pharmacies = userDatabase.getAllUsers()
        }
    }



}