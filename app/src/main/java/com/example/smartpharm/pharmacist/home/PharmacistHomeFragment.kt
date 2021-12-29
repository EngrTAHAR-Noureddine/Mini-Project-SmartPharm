package com.example.smartpharm.pharmacist.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartpharm.R

class PharmacistHomeFragment : Fragment() {

    // afficher list des commandes

    companion object {
        fun newInstance() = PharmacistHomeFragment()
    }

    private lateinit var viewModel: PharmacistHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pharmacist_home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PharmacistHomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}