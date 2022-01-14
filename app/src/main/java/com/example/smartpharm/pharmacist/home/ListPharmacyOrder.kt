package com.example.smartpharm.pharmacist.home

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpharm.R

class ListPharmacyOrder {

}

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val item = view.findViewById<View>(R.id.itemOrderPharmacy) as View
    val testItem = view.findViewById<TextView>(R.id.testItem2) as TextView

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