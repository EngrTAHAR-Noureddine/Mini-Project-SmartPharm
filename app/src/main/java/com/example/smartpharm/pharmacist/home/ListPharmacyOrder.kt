package com.example.smartpharm.pharmacist.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpharm.R
import com.example.smartpharm.firebase.controllers.orders.OrderController.deleteOrder
import com.example.smartpharm.firebase.models.Order

class ListPharmacyOrder(val context: FragmentActivity?, var data:List<Order>?):
    RecyclerView.Adapter<PharmacyOrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacyOrderViewHolder {
        return PharmacyOrderViewHolder(LayoutInflater.from(context).inflate(R.layout.pharmacy_order_item, parent, false))
    }

    override fun onBindViewHolder(holder: PharmacyOrderViewHolder, position: Int) {
        holder.testItem.text =if(data!=null && data!!.isNotEmpty()) data!![position].state+"-"+ data!![position].userEmail else ""
        holder.removeButton.isVisible = false
        holder.item.setOnClickListener {
            holder.removeButton.isVisible = !holder.removeButton.isVisible
        }
        holder.removeButton.setOnClickListener {
            if(!data.isNullOrEmpty()) deleteOrder(data!![position],context!!)
        }

    }

    override fun getItemCount() = if(data != null && data!!.isNotEmpty()) data!!.size else 0


}

class PharmacyOrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val item = view.findViewById<View>(R.id.itemOrderPharmacy) as View
    val testItem = view.findViewById<TextView>(R.id.testItem2) as TextView
    val removeButton = view.findViewById<ImageButton>(R.id.removeButton) as ImageButton


}

//TODO Recycle view OF Order for pharmacy
// TODO Recycle view OF Order for user
// TODO view Order fragment
// TODO ADD REMOVE ORDER -> TODO Update Order  when rejet (like cancel )  order from pharmacy remove it from both
// TODO ADD ACCEPT ORDER -> TODO Update Order
// TODO POPMenu of Ville
// TODO SEARCH BARs
// MAP AFTER
//UI Design

// */