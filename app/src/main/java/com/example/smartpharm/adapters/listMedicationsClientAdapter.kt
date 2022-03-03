package com.example.smartpharm.adapters

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpharm.R
import com.example.smartpharm.controllers.MedicationController.updateDate
import com.example.smartpharm.controllers.MedicationController.updateFirstDays
import com.example.smartpharm.controllers.MyAlarmManager.cancelAlarm
import com.example.smartpharm.controllers.MyAlarmManager.setAlarm
import com.example.smartpharm.models.MyMedications
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class listMedicationsClientAdapter(val context: FragmentActivity?, var data:List<MyMedications>):
    RecyclerView.Adapter<MyViewMedicationClientHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewMedicationClientHolder {
        return MyViewMedicationClientHolder(LayoutInflater.from(context).inflate(R.layout.item_medication_client, parent, false))
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "UnspecifiedImmutableFlag", "MissingPermission",
        "SimpleDateFormat"
    )
    override fun onBindViewHolder(holder: MyViewMedicationClientHolder, position: Int) {


        var currentPosition :Int = holder.adapterPosition
        val mTimePicker: TimePickerDialog?
        val hour = data[position].Launch!!["hour"]
        val minute = data[position].Launch!!["minute"]

        val mTimePickerDinner: TimePickerDialog?
        val hourDinner = data[position].Dinner!!["hour"]
        val minuteDinner = data[position].Dinner!!["minute"]

        context?.let{
            cancelAlarm(it, data[currentPosition].idMedication,0)
            cancelAlarm(it, data[currentPosition].idMedication,1)

            setAlarm(it,data[position].Launch!!["hour"]!!,data[position].Launch!!["minute"]!!, data[currentPosition].idMedication,0)
            setAlarm(it,data[position].Dinner!!["hour"]!!,data[position].Dinner!!["minute"]!!, data[currentPosition].idMedication,1)
        }

        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
        val firstDay = "${data[position].first_day!!["month"]!!}/${data[position].first_day!!["day"]!!}/${data[position].first_day!!["year"]!!}"
        val firstDate = sdf.parse(firstDay)
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH) +1
        val day = calender.get(Calendar.DAY_OF_MONTH)
        val currentDay = "$month/$day/$year"
        val secondDate = sdf.parse(currentDay)

        val diffInMillies = kotlin.math.abs(secondDate?.time!! - firstDate?.time!!)
        val diff: Long = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)
        Log.v("LEFT" , "diff : $diff month:$month ")

        data[currentPosition].Days = (data[currentPosition].Days - diff).toInt()
        data[currentPosition].first_day!!["month"] = month
        data[currentPosition].first_day!!["day"]= day
        data[currentPosition].first_day!!["year"] = year
        updateFirstDays(data[position] , context!!)



        mTimePicker = hour?.let {
            minute?.let { it1 ->
                TimePickerDialog(context,
                    { _, hourOfDay, minute ->
                        holder.inputTimeLunch.text = String.format("%d : %d", hourOfDay, minute)
                        data[currentPosition].Launch!!["hour"] = hourOfDay
                        data[currentPosition].Launch!!["minute"] = minute
                        updateDate(data[position],"Launch",context)
                        setAlarm(context,hourOfDay,minute, data[currentPosition].idMedication,0)
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
                        updateDate(data[position],"Dinner",context)
                        setAlarm(context,hourOfDay,minute, data[currentPosition].idMedication,1)
                    },
                    it, it1, true)
            }
        }

        holder.inputTimeLunch.setOnClickListener{
            cancelAlarm(context, data[currentPosition].idMedication,0)

            mTimePicker?.show()
        }


        holder.inputTimeDinner.setOnClickListener{
            cancelAlarm(context, data[currentPosition].idMedication,1)
            mTimePickerDinner?.show()

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