package com.example.smartpharm.client.demande_order

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartpharm.R

class DemandeOrderFragment : Fragment() {

    //interface pour capture photo et botton in bottom pour effectuer commande

    companion object {
        fun newInstance() = DemandeOrderFragment()
    }

    private lateinit var viewModel: DemandeOrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.demande_order_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DemandeOrderViewModel::class.java)
        // TODO: Use the ViewModel
    }

}