package com.example.smartpharm.order_detail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smartpharm.R
import com.example.smartpharm.client.demande_order.GridImageAdapter
import com.example.smartpharm.controllers.FileController
import com.example.smartpharm.controllers.FileController.destroyAllFiles
import com.example.smartpharm.controllers.FileController.listFile
import com.example.smartpharm.controllers.FileController.returnPhotos
import com.example.smartpharm.databinding.OderDetailFragmentBinding
import com.example.smartpharm.firebase.controllers.orders.OrderController.listState
import com.example.smartpharm.firebase.models.Order
import com.example.smartpharm.firebase.models.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.squareup.picasso.Picasso


class OderDetailFragment : Fragment() {


    private lateinit var viewModel: OderDetailViewModel
    private lateinit var mainBinding: OderDetailFragmentBinding

    private fun getData(file:String, string: String): String?{
        val prefUser = context?.getSharedPreferences(file, Context.MODE_PRIVATE)
        return prefUser?.getString(string,"")
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mainBinding = DataBindingUtil.inflate(inflater,R.layout.oder_detail_fragment,container,false)

        val gson = Gson()
        val json1: String = getData("Order", "orderDetail") ?: ""
        val json2: String = getData("UserProfile", "userProfile") ?: ""
        val order :Order? = gson.fromJson(json1, Order::class.java)
        val user : User? = gson.fromJson(json2, User::class.java)
        var type = ""

        if(user !=null) type = user.typeUser


        val viewModelFactory = OrderDetailFactory(mainBinding ,this.requireActivity(), type, order)

        val orderDetailViewModel = ViewModelProvider(this, viewModelFactory)[OderDetailViewModel::class.java]

        this.mainBinding.orderDetailViewModel = orderDetailViewModel
        this.mainBinding.lifecycleOwner = this

        mainBinding.GridRecycleView.layoutManager = GridLayoutManager(context, 3)
        //-------------------------------
        if(order!=null && user !=null) {
            if (user.typeUser == "Pharmacy") {
                Picasso.with(context).load(order.user!!["photoUser"]).fit().centerCrop()
                    .into(mainBinding.imageOrder)

                mainBinding.nameDetailPharmacy.text = order.user!!["nameUser"]
                mainBinding.locationDetailPharmacy.text = order.user!!["locationUser"]

                if (order.state == listState[0]) {
                    mainBinding.buttonRejectOrder.isVisible = true
                    mainBinding.buttonAcceptOrder.isVisible = true
                } else {
                    mainBinding.buttonRejectOrder.isVisible = false
                    mainBinding.buttonAcceptOrder.isVisible = false
                }
            } else {
                if (order.state == listState[0]) {
                    mainBinding.buttonRejectOrder.isVisible = false
                    mainBinding.buttonAcceptOrder.text = "Annuler"
                    mainBinding.buttonAcceptOrder.isVisible = true
                } else {
                    mainBinding.buttonRejectOrder.isVisible = false
                    mainBinding.buttonAcceptOrder.isVisible = false
                }
                Log.v("PHOTOTAG","photo ${order.pharmacy!!["photoPharmacy"]}")
                Picasso.with(context).load(order.pharmacy!!["photoPharmacy"]).fit().centerCrop()
                    .into(mainBinding.imageOrder)
                mainBinding.nameDetailPharmacy.text = order.pharmacy!!["namePharmacy"]
                mainBinding.locationDetailPharmacy.text = order.pharmacy!!["locationPharmacy"]
            }

            returnPhotos(order.photoOrders!!)
            listFile.observe(
                viewLifecycleOwner
            ) {
                mainBinding.GridRecycleView.adapter = GridImageAdapter(activity, it)
            }
            mainBinding.inputNote.setText(order.note)
        }
        //-------------------------------

        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        if(navBar != null){
            navBar.isVisible = false
        }
        return mainBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyAllFiles()
    }


}