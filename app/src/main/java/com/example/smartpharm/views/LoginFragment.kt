package com.example.smartpharm.views
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import com.example.smartpharm.controllers.UserController
import com.example.smartpharm.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private var _email = MutableLiveData<String>()
    private var _password  = MutableLiveData<String>()
    private var pref : SharedPreferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = LoginFragmentBinding.inflate(inflater,container,false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLogIn.setOnClickListener {
            _email.value = if(binding.inputEmail.text != null) binding.inputEmail.text.toString() else "none"
            _password.value = if(binding.inputPassword.text != null) binding.inputPassword.text.toString() else "none"

            if(binding.inputEmail.text != null && binding.inputPassword.text != null && _email.value!!.isNotEmpty() && _password.value!!.isNotEmpty()){

                binding.progressBarLogin.isVisible =  true
                binding.buttonLogIn.isVisible = false

                UserController.loginUser(_email.value!!, _password.value!!, requireActivity())

            }else{
                val text = "email or password is Empty ${binding.inputEmail.text}"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(activity, text, duration)
                toast.show()
            }
        }
    }



}