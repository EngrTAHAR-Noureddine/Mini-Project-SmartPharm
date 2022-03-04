package com.example.smartpharm.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartpharm.R
import com.example.smartpharm.databinding.PharmacistHomeFragmentBinding
import com.example.smartpharm.controllers.OrderController
import com.example.smartpharm.controllers.OrderController.listState
import com.example.smartpharm.controllers.OrderController.searchOrder
import com.example.smartpharm.models.Order
import com.example.smartpharm.models.User
import com.example.smartpharm.adapters.ListPharmacyOrder
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView
import com.google.gson.Gson

class PharmacistHomeFragment : Fragment() {
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


        binding = PharmacistHomeFragmentBinding.inflate(inflater,container,false)


        this.binding.recyclerViewPharmacyOrders.layoutManager = LinearLayoutManager(activity)
        OrderController.getOrderOf(user, requireContext())



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        OrderController.listOrders.observe(
            viewLifecycleOwner
        ) {
            var list: List<Order>? =
                it?.filter { item -> item.state != listState[1] }?.sortedBy { o -> o.state }
            binding.progressBarClientHome.isVisible = false
            binding.textNotFound.isVisible = it.isNullOrEmpty()
            this.binding.recyclerViewPharmacyOrders.isVisible = true

            this.binding.recyclerViewPharmacyOrders.adapter = ListPharmacyOrder(activity, list)
        }

        val navBar = activity?.findViewById<BubbleNavigationConstraintView>(R.id.bottom_navigation_ph)
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
    }

    override fun onStart() {
        super.onStart()
        OrderController.getOrderOf(user, requireContext())
    }


}