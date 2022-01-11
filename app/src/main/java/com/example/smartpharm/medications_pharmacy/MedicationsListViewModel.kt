package com.example.smartpharm.medications_pharmacy

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartpharm.databinding.MedicationsListFragmentBinding
import com.example.smartpharm.firebase.controllers.medications.MedicationController
import com.example.smartpharm.firebase.controllers.medications.MedicationController.getMedicationOf
import com.example.smartpharm.firebase.models.Medication
import com.example.smartpharm.firebase.models.User

class MedicationsListViewModel(val user: User?, private val binding: MedicationsListFragmentBinding, private val context: FragmentActivity): ViewModel() {
    val listMedications : MutableLiveData<List<Medication>?>
        get() = MedicationController.listMedications

    init{
            getMedicationOf(user,context)
         }
}