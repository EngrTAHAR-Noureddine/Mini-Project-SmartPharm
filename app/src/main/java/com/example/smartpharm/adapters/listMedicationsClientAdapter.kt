package com.example.smartpharm.adapters

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpharm.R
import com.example.smartpharm.controllers.MedicationController.updateDate
import com.example.smartpharm.models.MyMedications
import com.example.smartpharm.views.SettingsFragment
import com.squareup.picasso.Picasso
import java.util.*

class listMedicationsClientAdapter(val context: FragmentActivity?, var data:List<MyMedications>):
    RecyclerView.Adapter<MyViewMedicationClientHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewMedicationClientHolder {
        return MyViewMedicationClientHolder(LayoutInflater.from(context).inflate(R.layout.item_medication_client, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewMedicationClientHolder, position: Int) {
        var currentPosition :Int = holder.adapterPosition
        val mTimePicker: TimePickerDialog?
        val hour = data[position].Launch!!["hour"]
        val minute = data[position].Launch!!["minute"]

        val mTimePickerDinner: TimePickerDialog?
        val hourDinner = data[position].Dinner!!["hour"]
        val minuteDinner = data[position].Dinner!!["minute"]

        mTimePicker = hour?.let {
            minute?.let { it1 ->
                TimePickerDialog(context,
                    { _, hourOfDay, minute ->
                        holder.inputTimeLunch.text = String.format("%d : %d", hourOfDay, minute)
                        data[currentPosition].Launch!!["hour"] = hourOfDay
                        data[currentPosition].Launch!!["minute"] = minute
                        updateDate(data[position],"Launch")
                    },
                    it, it1, true)
            }
        }


        mTimePickerDinner = hourDinner?.let {
            minuteDinner?.let { it1 ->
                TimePickerDialog(context,
                    { _, hourOfDay, minute ->
                        holder.inputTimeDinner.text = String.format("%d : %d", hourOfDay, minute)
                        data[currentPosition].Dinner!!["hour"] = hourOfDay
                        data[currentPosition].Dinner!!["minute"] = minute
                        updateDate(data[position],"Dinner")
                    },
                    it, it1, true)
            }
        }

        holder.inputTimeLunch.setOnClickListener{
            mTimePicker?.show()
            Log.v("TIME","data : ${data[currentPosition].Launch!!["hour"]}")
        }
        holder.inputTimeDinner.setOnClickListener{
            mTimePickerDinner?.show()
            Log.v("TIME","data : ${data[currentPosition].Dinner!!["hour"]}")
        }

        holder.inputDays.text = data[position].Days.toString()
        holder.inputDescription.text = data[position].description
        holder.inputNameMedication.text = data[position].Name
        holder.inputTimeDinner.text = String.format("%d : %d", data[position].Dinner!!["hour"], data[position].Dinner!!["minute"])

        holder.inputTimeLunch.text = String.format("%d : %d", data[position].Launch!!["hour"], data[position].Launch!!["minute"])

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