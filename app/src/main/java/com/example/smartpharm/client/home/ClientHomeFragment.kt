package com.example.smartpharm.client.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartpharm.R
import com.example.smartpharm.databinding.ClientHomeFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class ClientHomeFragment : Fragment() {

    private lateinit var binding: ClientHomeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.client_home_fragment,container,false)

        val viewModelFactory = ClientHomeViewModelFactory( binding ,this.requireActivity())

        val clientHomeViewModel = ViewModelProvider(this, viewModelFactory)[ClientHomeViewModel::class.java]

        binding.clientHomeViewModel = clientHomeViewModel
        binding.lifecycleOwner = this

        this.binding.recycleViewPharmacies.layoutManager = LinearLayoutManager(activity)

        clientHomeViewModel.pharmacies.observe(
            viewLifecycleOwner
        ) {
            it?.let {
                binding.progressBarClientHome.isVisible = false
                this.binding.recycleViewPharmacies.isVisible = true
                this.binding.recycleViewPharmacies.adapter = ListPharmacistsAdapter(activity, it)
            }
        }
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        if(navBar != null){
            navBar.isVisible = true
        }
        return binding.root
    }



}