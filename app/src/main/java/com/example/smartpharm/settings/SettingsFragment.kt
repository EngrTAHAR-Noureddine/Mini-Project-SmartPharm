package com.example.smartpharm.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.smartpharm.login.LoginActivity
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
        binding.buttonLogOut.setOnClickListener{
            val pref = activity?.getSharedPreferences("TypeUserFile", Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor? = pref?.edit()
            editor?.apply{
                putString("typeUserFile",null)
            }?.apply()
            val prefUser = activity?.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
            val editorUser : SharedPreferences.Editor? = prefUser?.edit()
            editorUser?.apply{
                putString("userProfile","")
            }?.apply()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return binding.root
    }



}