package com.example.smartpharm.medications_pharmacy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpharm.R
import com.example.smartpharm.firebase.models.Medication


class MedicationListAdapter(val context: FragmentActivity?, var data:List<Medication>):
    RecyclerView.Adapter<MyViewMedicationHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewMedicationHolder {
        return MyViewMedicationHolder(LayoutInflater.from(context).inflate(R.layout.pharmacy_medication_item_both, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewMedicationHolder, position: Int) {
        holder.nameTest.text = data[position].nameMedication
    }

    override fun getItemCount() = data.size
}

class MyViewMedicationHolder(view: View) : RecyclerView.ViewHolder(view) {
    val item = view.findViewById(R.id.ItemMedicationPharmacy) as View
    val nameTest = view.findViewById(R.id.testItem) as TextView
}