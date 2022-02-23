package com.example.smartpharm.views


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.smartpharm.R
import com.example.smartpharm.databinding.DialogAddMedicationFragmentBinding
import com.example.smartpharm.controllers.MedicationController.postMedication
import com.example.smartpharm.viewmodels.DialogAddMedicationViewModel
import com.example.smartpharm.models.Medication
import com.example.smartpharm.models.User
import com.google.gson.Gson

class DialogAddMedicationFragment : DialogFragment() {



    private lateinit var viewModel: DialogAddMedicationViewModel
    private lateinit var binding : DialogAddMedicationFragmentBinding
    private fun getData(file:String, string: String): String?{
        val prefUser = activity?.getSharedPreferences(file, Context.MODE_PRIVATE)
        return prefUser?.getString(string,"")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.dialog_add_medication_fragment,container,false)

        binding.CancelMedication.setOnClickListener {
            this.dismiss()
        }
        binding.AddMedication.setOnClickListener {
            val gson = Gson()
            val json2: String = getData("UserProfile", "userProfile") ?: ""
            val user: User? = gson.fromJson(json2, User::class.java)
            val name : String? = if(binding.nameMedication.text!=null) binding.nameMedication.text.toString() else null
            if(user!=null && name!=null){
                val medication = Medication(
                    emailPharmacy= user.emailUser,
                    nameMedication = name
                    )
                postMedication(medication,requireContext())
                this.dismiss()
            }else if(name != null){
                this.dismiss()
                Toast.makeText(activity,"Se connecter",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity,"Remplir le Nom de Medication",Toast.LENGTH_SHORT).show()
            }

        }


        return binding.root
    }





}