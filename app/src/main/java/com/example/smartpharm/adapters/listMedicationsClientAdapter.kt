package com.example.smartpharm.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpharm.R
import com.example.smartpharm.models.MyMedications
import com.example.smartpharm.views.SettingsFragment
import com.squareup.picasso.Picasso

class listMedicationsClientAdapter(val context: FragmentActivity?, var data:List<MyMedications>):
    RecyclerView.Adapter<MyViewMedicationClientHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewMedicationClientHolder {
        return MyViewMedicationClientHolder(LayoutInflater.from(context).inflate(R.layout.item_medication_client, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewMedicationClientHolder, position: Int) {
        holder.inputDays.text = data[position].Days.toString()
        holder.inputDescription.text = data[position].description
        holder.inputNameMedication.text = data[position].Name
        holder.inputTimeDinner.text = data[position].Dinner!!["hour"].toString()+":"+data[position].Dinner!!["minute"].toString()
        holder.inputTimeLunch.text = data[position].Launch!!["hour"].toString()+":"+data[position].Launch!!["minute"].toString()
        Picasso.with(context).load(data[position].photo).fit().centerCrop().into(holder.imageMedicationClient)
    }

    override fun getItemCount(): Int = data.size
}

class MyViewMedicationClientHolder(view: View) : RecyclerView.ViewHolder(view) {
    val item = view.findViewById(R.id.itemMedicationClient) as View
    val imageMedicationClient = view.findViewById(R.id.imageMedicationClient) as ImageView
    val inputNameMedication = view.findViewById(R.id.inputNameMedication) as TextView
    val inputDays = view.findViewById(R.id.inputDays) as TextView
    val inputDescription = view.findViewById(R.id.inputDescription) as TextView
    val inputTimeLunch = view.findViewById(R.id.inputTimeLunch) as TextView
    val inputTimeDinner = view.findViewById(R.id.inputTimeDinner) as TextView
}