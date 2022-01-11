package com.example.smartpharm.client.demande_order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.smartpharm.R
import com.example.smartpharm.databinding.DemandeOrderFragmentBinding

class DemandeOrderFragment : Fragment() {

    private lateinit var binding: DemandeOrderFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.demande_order_fragment,container,false)




        return binding.root
    }



}