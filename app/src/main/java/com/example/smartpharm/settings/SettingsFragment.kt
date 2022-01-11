package com.example.smartpharm.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartpharm.R
import com.example.smartpharm.databinding.SettingsFragmentBinding
import com.example.smartpharm.firebase.models.User
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class SettingsFragment : Fragment() {
    private lateinit var binding : SettingsFragmentBinding

    private fun getDataUser(): String?{
        val prefUser = activity?.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        return prefUser?.getString("userProfile","")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val gson = Gson()
        val json2 :String = if(getDataUser()!=null) getDataUser()!! else ""
        val user : User? = gson.fromJson(json2, User::class.java)

        binding = DataBindingUtil.inflate(inflater,R.layout.settings_fragment,container,false)
        binding.lifecycleOwner = this

        if(user!=null) {
            Picasso.with(context).load(user.photoUser).fit().centerCrop().into(binding.PhotoUser)
            this.binding.recycleViewSetting.layoutManager = LinearLayoutManager(activity)
            this.binding.recycleViewSetting.adapter = SettingRecycleViewAdapter(activity, user)
        }

        return binding.root
    }



}