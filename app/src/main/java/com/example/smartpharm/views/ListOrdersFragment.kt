package com.example.smartpharm.views


import android.content.Context
import android.content.Intent
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
import com.example.smartpharm.activities.LoginActivity
import com.example.smartpharm.databinding.ListOrdersFragmentBinding
import com.example.smartpharm.controllers.OrderController
import com.example.smartpharm.controllers.OrderController.searchOrder
import com.example.smartpharm.models.Order
import com.example.smartpharm.models.User
import com.example.smartpharm.adapters.ListPharmacyOrder
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

class ListOrdersFragment : Fragment() {

    private lateinit var binding: ListOrdersFragmentBinding
    private var user: User? = null

    private fun getData(file:String, string: String): String?{
        val prefUser = context?.getSharedPreferences(file, Context.MODE_PRIVATE)
        return prefUser?.getString(string,"")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val pref = context?.getSharedPreferences("TypeUserFile", Context.MODE_PRIVATE)
        val typeUser = pref?.getString("typeUserFile", null)
        if(typeUser.isNullOrEmpty()){
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        val gson = Gson()
        val json: String = getData("UserProfile", "userProfile") ?: ""
        user  = gson.fromJson(json, User::class.java)

        binding = ListOrdersFragmentBinding.inflate(inflater,container,false)

        OrderController.getOrderOf(user, requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        this.binding.RecycleViewListOrdersUser.layoutManager = LinearLayoutManager(activity)

        OrderController.listOrders.observe(
            viewLifecycleOwner
        ) {
            var list: List<Order>? = it?.sortedBy { o -> o.state }
            binding.progressBarClientHome.isVisible = false
            this.binding.RecycleViewListOrdersUser.isVisible = true
            binding.textNotFound.isVisible = it.isNullOrEmpty()
            this.binding.RecycleViewListOrdersUser.adapter = ListPharmacyOrder(activity, list)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        OrderController.listOrders.value = null
    }

    override fun onStart() {
        super.onStart()
        OrderController.getOrderOf(user, requireContext())
    }


}