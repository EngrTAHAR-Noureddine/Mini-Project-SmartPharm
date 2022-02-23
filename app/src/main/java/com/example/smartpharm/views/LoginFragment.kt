package com.example.smartpharm.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.smartpharm.R
import com.example.smartpharm.databinding.LoginFragmentBinding
import com.example.smartpharm.viewmodel_factories.LogInViewModelFactory
import com.example.smartpharm.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.login_fragment,container,false)

        val viewModelFactory = LogInViewModelFactory( binding ,this.requireActivity())
        val logInViewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
        binding.loginViewModel = logInViewModel
        binding.lifecycleOwner = this

        return binding.root
    }



}