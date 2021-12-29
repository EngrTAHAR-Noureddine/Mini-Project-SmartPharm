package com.example.smartpharm.client.orders

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartpharm.R

class ListOrdersFragment : Fragment() {

    //list order pour client

    companion object {
        fun newInstance() = ListOrdersFragment()
    }

    private lateinit var viewModel: ListOrdersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_orders_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListOrdersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}