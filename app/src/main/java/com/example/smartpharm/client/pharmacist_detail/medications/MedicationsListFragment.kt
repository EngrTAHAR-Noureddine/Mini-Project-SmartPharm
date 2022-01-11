package com.example.smartpharm.client.pharmacist_detail.medications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartpharm.R

class MedicationsListFragment : Fragment() {

    private lateinit var viewModel: MedicationsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return inflater.inflate(R.layout.medications_list_fragment, container, false)
    }



}