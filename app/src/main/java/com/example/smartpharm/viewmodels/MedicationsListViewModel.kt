package com.example.smartpharm.viewmodels

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartpharm.databinding.MedicationsListFragmentBinding
import com.example.smartpharm.controllers.MedicationController
import com.example.smartpharm.controllers.MedicationController.getMedicationOf
import com.example.smartpharm.models.Medication
import com.example.smartpharm.models.User

class MedicationsListViewModel(val user: User?, private val binding: MedicationsListFragmentBinding, @SuppressLint(
    "StaticFieldLeak"
) private val context: FragmentActivity): ViewModel() {
    val listMedications : MutableLiveData<MutableList<Medication>?>
        get() = MedicationController.listMedications

    init{
            getMedicationOf(user,context)
         }


}