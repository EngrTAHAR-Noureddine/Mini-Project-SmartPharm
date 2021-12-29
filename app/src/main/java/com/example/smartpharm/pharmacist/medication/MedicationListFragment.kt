package com.example.smartpharm.pharmacist.medication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartpharm.R

class MedicationListFragment : Fragment() {

    //afficher list des medicaments et botton pour add medicament (just UI)
    companion object {
        fun newInstance() = MedicationListFragment()
    }

    private lateinit var viewModel: MedicationListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.medication_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MedicationListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}