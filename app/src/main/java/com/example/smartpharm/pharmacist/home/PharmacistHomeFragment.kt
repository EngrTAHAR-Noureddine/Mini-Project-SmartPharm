package com.example.smartpharm.pharmacist.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartpharm.R
import com.example.smartpharm.databinding.PharmacistHomeFragmentBinding
import com.example.smartpharm.firebase.controllers.orders.OrderController.listState
import com.example.smartpharm.firebase.models.Order
import com.example.smartpharm.firebase.models.User
import com.google.gson.Gson

class PharmacistHomeFragment : Fragment() {

    private lateinit var viewModel: PharmacistHomeViewModel
    private lateinit var binding: PharmacistHomeFragmentBinding

    private fun getData(file:String, string: String): String?{
        val prefUser = context?.getSharedPreferences(file, Context.MODE_PRIVATE)
        return prefUser?.getString(string,"")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val gson = Gson()
        val json2: String = getData("UserProfile", "userProfile") ?: ""

        val user : User? = gson.fromJson(json2, User::class.java)


        binding = DataBindingUtil.inflate(inflater,R.layout.pharmacist_home_fragment,container,false)

        val viewModelFactory = PharmacieHomeViewModelFactory( binding ,this.requireActivity(),user)

        val pharmacistHomeViewModel = ViewModelProvider(this, viewModelFactory)[PharmacistHomeViewModel::class.java]

        binding.lifecycleOwner = this

        this.binding.recyclerViewPharmacyOrders.layoutManager = LinearLayoutManager(activity)

        pharmacistHomeViewModel.listPharmacyOrders.observe(
            viewLifecycleOwner,{
                var list : List<Order>? =if(it!=null) it.filter { item -> item.state != listState[1] }.sortedBy { o -> o.state } else null
                this.binding.recyclerViewPharmacyOrders.adapter = ListPharmacyOrder(activity,list)
            }
        )

        return binding.root
    }


}