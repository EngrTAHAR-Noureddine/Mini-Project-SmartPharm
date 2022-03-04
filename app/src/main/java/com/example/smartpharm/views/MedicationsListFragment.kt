package com.example.smartpharm.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartpharm.databinding.MedicationsListFragmentBinding
import com.example.smartpharm.controllers.MedicationController.searchMedication
import com.example.smartpharm.adapters.MedicationListAdapter
import com.example.smartpharm.controllers.MedicationController
import com.example.smartpharm.models.User
import com.google.gson.Gson

class MedicationsListFragment : Fragment() {

    private lateinit var binding: MedicationsListFragmentBinding
    private var user : User? = null


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

        binding = MedicationsListFragmentBinding.inflate(inflater,container,false)
        val gson = Gson()
        val json :String = if(getDataPharmacy()!=null) getDataPharmacy()!! else ""
        user = gson.fromJson(json, User::class.java)

        val json2 :String = if(getDataUser()!=null) getDataUser()!! else ""
        val user2 : User? = gson.fromJson(json2, User::class.java)

        if(user2 != null && user2.typeUser=="Pharmacy") user = user2

        MedicationController.getMedicationOf(user, requireActivity())
        binding.FABAddMedication.isVisible = user2 != null && user2.typeUser == "Pharmacy"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.binding.recycleViewMedications.layoutManager = LinearLayoutManager(activity)

        MedicationController.listMedications.observe(
            viewLifecycleOwner
        ) {
            binding.textNotFound.isVisible = true
            it?.let {
                binding.progressBarMedication.isVisible = false
                binding.textNotFound.isVisible = false
                binding.textNotFound.isVisible = it.isNullOrEmpty()
                this.binding.recycleViewMedications.isVisible = true
                this.binding.recycleViewMedications.adapter = MedicationListAdapter(activity, it)
            }
            binding.progressBarMedication.visibility = View.INVISIBLE

        }

        binding.FABAddMedication.setOnClickListener{
            DialogAddMedicationFragment().showNow(this.parentFragmentManager,"DialogBox")
        }

        binding.InputSearchMedication.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.InputSearchMedication.clearFocus()
                searchMedication(query,user, context as FragmentActivity)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                searchMedication(newText,user, context as FragmentActivity)
                return false
            }


        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MedicationController.listMedications.value = null
    }



}