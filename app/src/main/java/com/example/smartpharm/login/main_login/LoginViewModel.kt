package com.example.smartpharm.login.main_login

import android.content.SharedPreferences
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartpharm.firebase.controllers.UserController.loginUser
import com.example.smartpharm.databinding.LoginFragmentBinding





class LoginViewModel(private val binding: LoginFragmentBinding,
                     private val context : FragmentActivity) : ViewModel() {


    private var _email = MutableLiveData<String>()
    private var _password  = MutableLiveData<String>()
    private var pref : SharedPreferences? = null

    val email: LiveData<String>
        get() = _email
    val password: LiveData<String>
        get() = _password


    init {
        _email.value = ""
        _password.value = ""
    }

    fun onCLickLogIn(){
        _email.value = if(binding.inputEmail.text != null) binding.inputEmail.text.toString() else "none"
        _password.value = if(binding.inputPassword.text != null) binding.inputPassword.text.toString() else "none"

        if(binding.inputEmail.text != null && binding.inputPassword.text != null && _email.value!!.isNotEmpty() && _password.value!!.isNotEmpty()){

           loginUser(_email.value!!,_password.value!!, context)

        }else{
            val text = "email or password is Empty"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(context, text, duration)
            toast.show()
        }

    }

}