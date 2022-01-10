package com.example.smartpharm.client.pharmacist_detail.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartpharm.R

class PharmacyDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = PharmacyDetailsFragment()
    }

    private lateinit var viewModel: PharmacyDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pharmacy_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PharmacyDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}