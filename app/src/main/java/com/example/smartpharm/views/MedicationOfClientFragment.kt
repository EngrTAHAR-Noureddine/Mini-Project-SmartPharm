package com.example.smartpharm.views


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartpharm.activities.LoginActivity
import com.example.smartpharm.adapters.listMedicationsClientAdapter
import com.example.smartpharm.controllers.MedicationController.getMedicationOfClient
import com.example.smartpharm.controllers.MedicationController.listMedicationsUser
import com.example.smartpharm.controllers.gson
import com.example.smartpharm.databinding.MedicationOfClientFragmentBinding
import com.example.smartpharm.models.User

class MedicationOfClientFragment : Fragment() {

    private lateinit var binding : MedicationOfClientFragmentBinding
    private var user : User? = null

    private fun getDataUser(): String?{
        val prefUser = activity?.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        return prefUser?.getString("userProfile","")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val pref = context?.getSharedPreferences("TypeUserFile", Context.MODE_PRIVATE)
        val typeUser = pref?.getString("typeUserFile", null)
        if(typeUser.isNullOrEmpty()){
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        val json2 :String = if(getDataUser()!=null) getDataUser()!! else ""
        user = gson.fromJson(json2, User::class.java)
        if(user !=null ) getMedicationOfClient(user,requireActivity())
        binding = MedicationOfClientFragmentBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycleViewMedicationsClient.layoutManager = LinearLayoutManager(activity)
        listMedicationsUser.observe(viewLifecycleOwner){
            it?.let {
                this.binding.recycleViewMedicationsClient.adapter = listMedicationsClientAdapter(activity, it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listMedicationsUser.value = null
    }



}