package com.example.smartpharm.client.home

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.smartpharm.database.users.UsersDao
import com.example.smartpharm.databinding.ClientHomeFragmentBinding

class ClientHomeViewModel(private val userDatabase: UsersDao, private val binding: ClientHomeFragmentBinding
                          , private val context : FragmentActivity) : ViewModel() {

    // TODO: Implement the ViewModel
}