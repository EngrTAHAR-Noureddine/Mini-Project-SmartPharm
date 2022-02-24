package com.example.smartpharm.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartpharm.R
import com.example.smartpharm.databinding.PharmacistHomeFragmentBinding
import com.example.smartpharm.controllers.OrderController
import com.example.smartpharm.controllers.OrderController.listState
import com.example.smartpharm.controllers.OrderController.searchOrder
import com.example.smartpharm.models.Order
import com.example.smartpharm.models.User
import com.example.smartpharm.adapters.ListPharmacyOrder
import com.example.smartpharm.viewmodel_factories.PharmacieHomeViewModelFactory
import com.example.smartpharm.viewmodels.PharmacistHomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

class PharmacistHomeFragment : Fragment() {

    private lateinit var viewModel: PharmacistHomeViewModel
    private lateinit var binding: PharmacistHomeFragmentBinding
    private var user: User? = null

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

        user = gson.fromJson(json2, User::class.java)


        binding = DataBindingUtil.inflate(inflater,R.layout.pharmacist_home_fragment,container,false)

        val viewModelFactory = PharmacieHomeViewModelFactory( binding ,this.requireActivity(),user)

        val pharmacistHomeViewModel = ViewModelProvider(this, viewModelFactory)[PharmacistHomeViewModel::class.java]

        binding.lifecycleOwner = this

        this.binding.recyclerViewPharmacyOrders.layoutManager = LinearLayoutManager(activity)

        pharmacistHomeViewModel.listPharmacyOrders.observe(
            viewLifecycleOwner
        ) {
            var list: List<Order>? =
                it?.filter { item -> item.state != listState[1] }?.sortedBy { o -> o.state }
            binding.progressBarClientHome.isVisible = false
            binding.textNotFound.isVisible = it.isNullOrEmpty()
            this.binding.recyclerViewPharmacyOrders.isVisible = true

            this.binding.recyclerViewPharmacyOrders.adapter = ListPharmacyOrder(activity, list)
        }

        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        if(navBar != null){
            navBar.isVisible = true
        }

        binding.InputSearchOrders.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.InputSearchOrders.clearFocus()
                searchOrder(query, user, context as FragmentActivity)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                searchOrder(newText, user, context as FragmentActivity)
                return false
            }
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        OrderController.getOrderOf(user, requireContext())
    }


}