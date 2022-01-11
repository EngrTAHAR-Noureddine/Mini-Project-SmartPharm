package com.example.smartpharm.client.pharmacist_detail.medications

import android.content.Context
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
import com.example.smartpharm.databinding.MedicationsListFragmentBinding
import com.example.smartpharm.firebase.models.User
import com.google.gson.Gson

class MedicationsListFragment : Fragment() {

    private lateinit var viewModel: MedicationsListViewModel
    private lateinit var binding: MedicationsListFragmentBinding

    private fun getDataPharmacy(): String?{
        val prefUser = activity?.getSharedPreferences("PharmacyProfile", Context.MODE_PRIVATE)
        return prefUser?.getString("pharmacyProfile","")
    }
    private fun getDataUser(): String?{
        val prefUser = activity?.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        return prefUser?.getString("userProfile","")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.medications_list_fragment,container,false)
        val gson = Gson()
        val json :String = if(getDataPharmacy()!=null) getDataPharmacy()!! else ""
        var user : User? = gson.fromJson(json, User::class.java)

        val json2 :String = if(getDataUser()!=null) getDataUser()!! else ""
        val user2 : User? = gson.fromJson(json2, User::class.java)

        if(user2 != null && user2.typeUser=="Pharmacy") user = user2

        val viewModelFactory = MedicationsListViewModelFactory(user, binding ,this.requireActivity())

        val medicationsListViewModel = ViewModelProvider(this, viewModelFactory)[MedicationsListViewModel::class.java]

        binding.medicationsListViewModel = medicationsListViewModel
        binding.lifecycleOwner = this

        binding.FABAddMedication.isVisible = user2 != null && user2.typeUser == "Pharmacy"

        this.binding.recycleViewMedications.layoutManager = LinearLayoutManager(activity)

        medicationsListViewModel.listMedications.observe(
            viewLifecycleOwner,  {
                it?.let{
                    binding.progressBarMedication.isVisible = false
                    this.binding.recycleViewMedications.isVisible = true
                    this.binding.recycleViewMedications.adapter = MedicationListAdapter(activity,it)
                }
            }
        )


        return binding.root
    }



}