package com.example.smartpharm.pharmacist.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpharm.R
import com.example.smartpharm.firebase.controllers.orders.OrderController
import com.example.smartpharm.firebase.controllers.orders.OrderController.listState
import com.example.smartpharm.firebase.models.Order
import com.google.gson.Gson

class ListPharmacyOrder(val context: FragmentActivity?, var data:List<Order>?):
    RecyclerView.Adapter<PharmacyOrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacyOrderViewHolder {
        return PharmacyOrderViewHolder(LayoutInflater.from(context).inflate(R.layout.pharmacy_order_item, parent, false))
    }

    @SuppressLint("CommitPrefEdits")
    override fun onBindViewHolder(holder: PharmacyOrderViewHolder, position: Int) {
        holder.testItem.text =if(data!=null && data!!.isNotEmpty()) data!![position].state+"\n"+ data!![position].idOrder else ""
        holder.removeButton.isVisible = false
        holder.item.setOnClickListener {

            if(data!=null && data!!.isNotEmpty()){
                val order:Order? = data!![position]
                val gson = Gson()
                val prefOrder = context?.getSharedPreferences("Order", Context.MODE_PRIVATE)
                val editorOrder : SharedPreferences.Editor? = prefOrder?.edit()
                val json = gson.toJson(order)
                editorOrder?.apply{
                    putString("orderDetail",json)
                }?.apply()

                val userType: String = getData("TypeUserFile", "typeUserFile") ?: ""
                if(data!![position].state == listState[2]){
                    holder.removeButton.isVisible = !holder.removeButton.isVisible
                }else{
                    if(userType == "Pharmacy"){
                        context?.findNavController(R.id.myPharmacyNavHostFragment)?.navigate(R.id.action_destination_to_Pharmacy_Order_Detail)
                    }else{
                        context?.findNavController(R.id.myNavHostFragment)?.navigate(R.id.action_destination_toOrder_Detail)
                    }
                }
            }
        }

        holder.removeButton.setOnClickListener {
           if(data!=null) OrderController.deleteOrder(data!![position], context!!)
        }

    }
    private fun getData(file:String, string: String): String?{
        val prefUser = context?.getSharedPreferences(file, Context.MODE_PRIVATE)
        return prefUser?.getString(string,"")
    }

    override fun getItemCount() = if(data != null && data!!.isNotEmpty()) data!!.size else 0


}

class PharmacyOrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val item = view.findViewById(R.id.itemOrderPharmacy) as View
    val testItem = view.findViewById(R.id.testItem2) as TextView
    val removeButton = view.findViewById(R.id.removeButton) as ImageButton
}
