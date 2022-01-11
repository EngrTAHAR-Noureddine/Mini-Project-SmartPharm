package com.example.smartpharm.client.home

import android.content.Context
import android.content.SharedPreferences
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
import com.google.gson.Gson

class ListPharmacistsAdapter(val context: FragmentActivity?, var data:List<User>):
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.client_item_pharmacist, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.namePharmacy.text = data[position].name
        holder.locationPharmacy.text = data[position].locationUser

        holder.item.setOnClickListener {

            val pharmacy = User(
                name = data[position].name,
                locationUser = data[position].locationUser,
                passwordUser = data[position].passwordUser,
                emailUser = data[position].emailUser,
                phoneNumber = data[position].phoneNumber,
                photoUser = data[position].photoUser,
                typeUser = data[position].typeUser,
                facebookAccount = data[position].facebookAccount,
                instagramAccount = data[position].instagramAccount,
            )

            val gson = Gson()
            val prefUser = context?.getSharedPreferences("PharmacyProfile", Context.MODE_PRIVATE)
            val editorUser : SharedPreferences.Editor? = prefUser?.edit()
            val json = gson.toJson(pharmacy)
            editorUser?.apply{
                putString("pharmacyProfile",json)
            }?.apply()


            context?.findNavController(R.id.myNavHostFragment)?.navigate(R.id.to_Client_Pharmacy_Detail)

        }
    }

    override fun getItemCount() = data.size
}

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val item = view.findViewById<View>(R.id.itemPharmacistClientHome) as View
    val namePharmacy = view.findViewById<TextView>(R.id.Pharmacy_Name) as TextView
    val locationPharmacy = view.findViewById<TextView>(R.id.Pharmacy_Location) as TextView
}