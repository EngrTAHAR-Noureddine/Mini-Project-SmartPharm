package com.example.smartpharm.medications_pharmacy.addmedication


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.smartpharm.R
import com.example.smartpharm.databinding.DialogAddMedicationFragmentBinding

class DialogAddMedicationFragment : DialogFragment() {



    private lateinit var viewModel: DialogAddMedicationViewModel
    private lateinit var binding : DialogAddMedicationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.dialog_add_medication_fragment,container,false)



        return binding.root
    }
    /*
    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()

        binding.DialogBox.minWidth = width
        binding.DialogBox.minHeight = height

    }
    */




}