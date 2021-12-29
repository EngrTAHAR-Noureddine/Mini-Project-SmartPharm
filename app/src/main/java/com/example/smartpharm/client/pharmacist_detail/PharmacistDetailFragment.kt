package com.example.smartpharm.client.pharmacist_detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartpharm.R

class PharmacistDetailFragment : Fragment() {

    // detail de pharmacist selectionner

    companion object {
        fun newInstance() = PharmacistDetailFragment()
    }

    private lateinit var viewModel: PharmacistDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pharmacist_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PharmacistDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}