package com.example.smartpharm.client.pharmacist_detail.medications

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartpharm.R

class MedicationsListFragment : Fragment() {

    // list des medicament de pharmacist

    companion object {
        fun newInstance() = MedicationsListFragment()
    }

    private lateinit var viewModel: MedicationsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.medications_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MedicationsListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}