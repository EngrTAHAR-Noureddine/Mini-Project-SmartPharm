package com.example.smartpharm.viewmodel_factories

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartpharm.databinding.LoginFragmentBinding
import com.example.smartpharm.viewmodels.LoginViewModel

class LogInViewModelFactory(private val binding: LoginFragmentBinding
                            ,private val context : FragmentActivity):ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(binding ,context ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}