package com.example.smartpharm.client.home

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.smartpharm.databinding.ClientHomeFragmentBinding
import com.example.smartpharm.firebase.controllers.UserController.getPharmacies
import com.example.smartpharm.firebase.controllers.users.ClientController

class ClientHomeViewModel( private val binding: ClientHomeFragmentBinding
                          , private val context : FragmentActivity) : ViewModel() {

    var pharmacies = ClientController.listPharmacies

    init {
        initializeListPharmacies()
    }

    private fun initializeListPharmacies() {
        getPharmacies(context)
    }



}