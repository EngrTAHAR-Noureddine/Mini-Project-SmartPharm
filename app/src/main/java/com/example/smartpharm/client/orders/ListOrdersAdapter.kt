package com.example.smartpharm.client.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpharm.R
import com.example.smartpharm.firebase.models.Order

class ListOrdersAdapter(val context: FragmentActivity?, var data:List<Order>?):
    RecyclerView.Adapter<UserOrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserOrderViewHolder {
        return UserOrderViewHolder(LayoutInflater.from(context).inflate(R.layout.client_order_item, parent, false))
    }

    override fun onBindViewHolder(holder: UserOrderViewHolder, position: Int) {
        holder.testItem.text =if(data!=null && data!!.isNotEmpty()) data!![position].state+"\n"+ data!![position].idOrder else ""

        holder.item.setOnClickListener {

        }

    }

    override fun getItemCount() = if(data != null && data!!.isNotEmpty()) data!!.size else 0
}

class UserOrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val item = view.findViewById<View>(R.id.ItemOrderUser) as View
    val testItem = view.findViewById<TextView>(R.id.TestItem) as TextView
}