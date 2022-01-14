package com.example.smartpharm.client.orders


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
import com.example.smartpharm.databinding.ListOrdersFragmentBinding
import com.example.smartpharm.firebase.models.Order
import com.example.smartpharm.firebase.models.User
import com.example.smartpharm.pharmacist.home.ListPharmacyOrder
import com.google.gson.Gson

class ListOrdersFragment : Fragment() {


    private lateinit var viewModel: ListOrdersViewModel
    private lateinit var binding: ListOrdersFragmentBinding

    private fun getData(file:String, string: String): String?{
        val prefUser = context?.getSharedPreferences(file, Context.MODE_PRIVATE)
        return prefUser?.getString(string,"")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val gson = Gson()
        val json: String = getData("UserProfile", "userProfile") ?: ""

        val user : User? = gson.fromJson(json, User::class.java)

        binding = DataBindingUtil.inflate(inflater,R.layout.list_orders_fragment,container,false)

        val viewModelFactory = ListOrdersViewModelFactory( binding ,this.requireActivity(),user)

        val clientHomeViewModel = ViewModelProvider(this, viewModelFactory)[ListOrdersViewModel::class.java]

        binding.lifecycleOwner = this

        this.binding.RecycleViewListOrdersUser.layoutManager = LinearLayoutManager(activity)

        clientHomeViewModel.listUserOrders.observe(
            viewLifecycleOwner,{
                var list : List<Order>? =if(it!=null) it.sortedBy { o -> o.state } else null
                this.binding.RecycleViewListOrdersUser.adapter = ListPharmacyOrder(activity,list)
            }
        )

        return binding.root
    }


}