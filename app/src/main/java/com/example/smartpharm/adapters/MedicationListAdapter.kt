package com.example.smartpharm.medications_pharmacy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpharm.R
import com.example.smartpharm.controllers.MedicationController.deleteMedication
import com.example.smartpharm.models.Medication
import com.example.smartpharm.models.User
import com.google.gson.Gson


class MedicationListAdapter(val context: FragmentActivity?, var data:List<Medication>):
    RecyclerView.Adapter<MyViewMedicationHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewMedicationHolder {
        return MyViewMedicationHolder(LayoutInflater.from(context).inflate(R.layout.pharmacy_medication_item_both, parent, false))
    }

    private fun getData(file:String, string: String): String?{
        val prefUser = context?.getSharedPreferences(file, Context.MODE_PRIVATE)
        return prefUser?.getString(string,"")
    }

    override fun onBindViewHolder(holder: MyViewMedicationHolder, position: Int) {
        holder.nameTest.text = data[position].nameMedication
        val gson = Gson()
        val json: String = getData("UserProfile", "userProfile") ?: ""
        val user : User? = gson.fromJson(json, User::class.java)
        if(user!=null && user!!.typeUser=="Pharmacy"){
            holder.item.setOnClickListener{
                holder.deleteMedication.isVisible = !holder.deleteMedication.isVisible
            }
            holder.deleteMedication.setOnClickListener {
                if(data!=null) deleteMedication(data[position],context!!)
            }
        }

    }

    override fun getItemCount() = data.size
}

class MyViewMedicationHolder(view: View) : RecyclerView.ViewHolder(view) {
    val item = view.findViewById(R.id.ItemMedicationPharmacy) as View
    val nameTest = view.findViewById(R.id.itemName) as TextView
    val deleteMedication = view.findViewById(R.id.DeleteMedication) as ImageButton
}