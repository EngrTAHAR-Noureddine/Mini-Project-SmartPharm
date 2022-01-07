package com.example.smartpharm.login.main_login

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartpharm.database.users.UsersDao
import com.example.smartpharm.databinding.LoginFragmentBinding

class LogInViewModelFactory(private val userDatabase: UsersDao,private val binding: LoginFragmentBinding
                            ,private val context : FragmentActivity):ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userDatabase, binding ,context ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}