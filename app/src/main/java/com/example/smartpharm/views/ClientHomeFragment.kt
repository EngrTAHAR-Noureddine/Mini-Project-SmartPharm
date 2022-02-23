package com.example.smartpharm.views


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartpharm.R
import com.example.smartpharm.viewmodels.ClientHomeViewModel
import com.example.smartpharm.viewmodel_factories.ClientHomeViewModelFactory
import com.example.smartpharm.client.home.ListPharmacistsAdapter
import com.example.smartpharm.databinding.ClientHomeFragmentBinding
import com.example.smartpharm.controllers.ClientController.searchPharmacy
import com.example.smartpharm.controllers.ClientController.searchPharmacyByProvince
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
            if(!it.isNullOrEmpty()){
                binding.textNotFound.isVisible = false
                binding.textResult.text = "Resultat : ${it.size}"
            }else{
                binding.textNotFound.isVisible = true
                binding.textResult.text ="Resultat : 0"
            }

        }
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        if(navBar != null){
            navBar.isVisible = true
        }



        binding.editTextTextPersonName.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.editTextTextPersonName.clearFocus()
                searchPharmacy(query,context as FragmentActivity)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                searchPharmacy(newText, context as FragmentActivity)
                return false
            }


        })

        binding.popMenuButton.setOnClickListener{
            val popupMenu = PopupMenu(context,it)
            popupMenu.menuInflater.inflate(R.menu.menu_provinces,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener{ item ->
                changeProvinceTo(item.itemId,requireContext())
            }
            popupMenu.show()
        }

        return binding.root
    }


    private fun changeProvinceTo(idProvince:Int,context:Context):Boolean{

        var province = when(idProvince){
            R.id.no_Province ->  "Wilaya"
            R.id.Province_adrar ->  getString(R.string.Province_adrar)
            R.id.Province_chlef -> getString(R.string.Province_chlef)
            R.id.Province_laghouat -> getString(R.string.Province_laghouat)
            R.id.Province_oum_el_bouaghi -> getString(R.string.Province_oum_el_bouaghi)
            R.id.Province_batna ->  getString(R.string.Province_batna)
            R.id.Province_bejaia ->  getString(R.string.Province_bejaia)
            R.id.Province_biskra ->  getString(R.string.Province_biskra)
            R.id.Province_bechar ->  getString(R.string.Province_bechar)
            R.id.Province_blida ->  getString(R.string.Province_blida)
            R.id.Province_bouira ->  getString(R.string.Province_bouira)


            R.id.Province_tamanrasset ->  getString(R.string.Province_tamanrasset)
            R.id.Province_tebessa ->  getString(R.string.Province_tebessa)
            R.id.Province_tlemcen ->  getString(R.string.Province_tlemcen)
            R.id.Province_tiaret ->  getString(R.string.Province_tiaret)
            R.id.Province_tizi_ouzou ->  getString(R.string.Province_tizi_ouzou)
            R.id.Province_alger ->  getString(R.string.Province_alger)
            R.id.Province_djelfa ->  getString(R.string.Province_djelfa)
            R.id.Province_jijel -> getString(R.string.Province_jijel)
            R.id.Province_setif ->  getString(R.string.Province_setif)
            R.id.Province_saida ->  getString(R.string.Province_saida)

            R.id.Province_skikda ->  getString(R.string.Province_skikda)
            R.id.Province_sidi_bel_abbes -> getString(R.string.Province_sidi_bel_abbes)
            R.id.Province_annaba -> getString(R.string.Province_annaba)
            R.id.Province_guelma -> getString(R.string.Province_guelma)
            R.id.Province_constantine -> getString(R.string.Province_constantine)
            R.id.Province_medea -> getString(R.string.Province_medea)
            R.id.Province_mostaganem -> getString(R.string.Province_mostaganem)
            R.id.Province_msila ->  getString(R.string.Province_msila)
            R.id.Province_mascara ->  getString(R.string.Province_mascara)
            R.id.Province_ouargla -> getString(R.string.Province_ouargla)

            R.id.Province_oran ->  getString(R.string.Province_oran)
            R.id.Province_elbayadh -> getString(R.string.Province_elbayadh)
            R.id.Province_illizi -> getString(R.string.Province_illizi)
            R.id.Province_bordj_bou_arreridj ->  getString(R.string.Province_bordj_bou_arreridj)
            R.id.Province_boumerdes ->  getString(R.string.Province_boumerdes)
            R.id.Province_el_tarf ->  getString(R.string.Province_el_tarf)
            R.id.Province_tindouf ->  getString(R.string.Province_tindouf)
            R.id.Province_tissemsilt ->  getString(R.string.Province_tissemsilt)
            R.id.Province_el_oued ->  getString(R.string.Province_el_oued)
            R.id.Province_khenchela ->  getString(R.string.Province_khenchela)

            R.id.Province_souk_ahras ->  getString(R.string.Province_souk_ahras)
            R.id.Province_tipaza -> getString(R.string.Province_tipaza)
            R.id.Province_mila -> getString(R.string.Province_mila)
            R.id.Province_ain_defla ->  getString(R.string.Province_ain_defla)
            R.id.Province_Naama -> getString(R.string.Province_Naama)
            R.id.Province_ain_temouchent -> getString(R.string.Province_ain_temouchent)
            R.id.Province_ghardaia -> getString(R.string.Province_ghardaia)
            R.id.Province_relizane -> getString(R.string.Province_relizane)
            R.id.Province_el_mghair -> getString(R.string.Province_el_mghair)
            R.id.Province_el_menia -> getString(R.string.Province_el_menia)

            R.id.Province_ouled_djellal -> getString(R.string.Province_ouled_djellal)
            R.id.Province_bordj_baji_mokhtar -> getString(R.string.Province_bordj_baji_mokhtar)
            R.id.Province_beni_abbes -> getString(R.string.Province_beni_abbes)
            R.id.Province_timimoun -> getString(R.string.Province_timimoun)
            R.id.Province_touggourt -> getString(R.string.Province_touggourt)
            R.id.Province_djanet -> getString(R.string.Province_djanet)
            R.id.Province_in_salah -> getString(R.string.Province_in_salah)
            R.id.Province_in_guezzam -> getString(R.string.Province_in_guezzam)

            else -> "Wilaya"
        }

        binding.popMenuButton.text = province

        searchPharmacyByProvince(province, context as FragmentActivity)


        return true
    }



}