package com.example.smartpharm.client.home

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartpharm.database.users.UsersDao
import com.example.smartpharm.databinding.ClientHomeFragmentBinding
import com.example.smartpharm.login.main_login.LoginViewModel

class ClientHomeViewModelFactory (private val userDatabase: UsersDao,
                                  private val binding: ClientHomeFragmentBinding
                                  ,private val context : FragmentActivity
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientHomeViewModel::class.java)) {
            return ClientHomeViewModel(userDatabase, binding ,context ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}