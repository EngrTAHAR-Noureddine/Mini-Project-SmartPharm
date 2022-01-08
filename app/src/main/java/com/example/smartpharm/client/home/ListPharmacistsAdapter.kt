package com.example.smartpharm.client.home

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpharm.R
import com.example.smartpharm.model.User

class ListPharmacistsAdapter(val context: FragmentActivity?, var data:List<User>):
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val item = view.findViewById<View>(R.id.itemPharmacistClientHome) as View
/*
    val name = view.findViewById<TextView>(R.id.NameVar) as TextView
    val phone = view.findViewById<TextView>(R.id.phoneVar) as TextView
    val address = view.findViewById<TextView>(R.id.adressVar) as TextView
*/
}