package com.example.smartpharm.views


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.smartpharm.R
import com.example.smartpharm.viewmodels.PharmacyDetailsViewModel
import com.example.smartpharm.databinding.PharmacyDetailsFragmentBinding
import com.example.smartpharm.models.User
import com.google.gson.Gson

class PharmacyDetailsFragment : Fragment() {


    private lateinit var viewModel: PharmacyDetailsViewModel
    private lateinit var binding:PharmacyDetailsFragmentBinding

    private fun getData(): String?{
        val prefUser = activity?.getSharedPreferences("PharmacyProfile", Context.MODE_PRIVATE)
        return prefUser?.getString("pharmacyProfile","")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.pharmacy_details_fragment, container, false)
        binding.lifecycleOwner = this

        val gson = Gson()
        val json :String = if(getData()!=null) getData()!! else ""
        val user : User? = gson.fromJson(json, User::class.java)

        if(user!=null){
            binding.nameDetailPharmacy.text = user.company
            binding.locationDetailPharmacy.text = user.locationUser
            binding.gmailDetailPharmacy.text = user.emailUser
            binding.FacebookAccountPharmacy.text = user.facebookAccount
            binding.InstagramPharmacy.text = user.instagramAccount
            binding.PhonePharmacy.text =user.phoneNumber
        }


        return binding.root
    }



}