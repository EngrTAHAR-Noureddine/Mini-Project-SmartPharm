package com.example.smartpharm.client.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpharm.R
import com.example.smartpharm.model.User

class ListPharmacistsAdapter(val context: FragmentActivity?, var data:List<User>):
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.client_item_pharmacist, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.namePharmacy.text = data[position].name
        holder.locationPharmacy.text = data[position].locationUser

        holder.item.setOnClickListener {
            val text = "you clicked on item $position"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(context, text, duration)
            toast.show()
            context?.findNavController(R.id.myNavHostFragment)?.navigate(R.id.to_Client_Pharmacy_Detail)



                /*
                val pharmacy = Pharmacie()
                pharmacy.name= data[position].name
                pharmacy.address = data[position].address
                pharmacy.phone =data[position].phone

                var bundle = bundleOf("pharmacy" to pharmacy)

                context?.findNavController(R.id.myNavHostFragment)?.navigate(R.id.action_listPharmacieFragment_to_pharmacieFragment,bundle)
                */
        }
    }

    override fun getItemCount() = data.size
}

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val item = view.findViewById<View>(R.id.itemPharmacistClientHome) as View
    val namePharmacy = view.findViewById<TextView>(R.id.Pharmacy_Name) as TextView
    val locationPharmacy = view.findViewById<TextView>(R.id.Pharmacy_Location) as TextView
}