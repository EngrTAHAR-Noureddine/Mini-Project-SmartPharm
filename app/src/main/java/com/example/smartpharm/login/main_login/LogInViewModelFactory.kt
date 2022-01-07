package com.example.smartpharm.login.main_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartpharm.database.users.UsersDao

class LogInViewModelFactory(private val userDatabase: UsersDao):ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}