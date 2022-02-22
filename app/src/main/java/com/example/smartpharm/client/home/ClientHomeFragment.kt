package com.example.smartpharm.client.home


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
import com.example.smartpharm.databinding.ClientHomeFragmentBinding
import com.example.smartpharm.firebase.controllers.orders.OrderController
import com.example.smartpharm.firebase.controllers.users.ClientController.searchPharmacy
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
                changeProvinceTo(item.itemId)
            }
            popupMenu.show()
        }

        return binding.root
    }


    private fun changeProvinceTo(idProvince:Int):Boolean{

        var province = when(idProvince){

            R.id.Province_adrar ->  R.string.Province_adrar.toString()
            R.id.Province_chlef -> R.string.Province_chlef.toString()
            R.id.Province_laghouat -> R.string.Province_laghouat.toString()
            R.id.Province_oum_el_bouaghi -> R.string.Province_oum_el_bouaghi.toString()
            R.id.Province_batna ->  R.string.Province_batna.toString()
            R.id.Province_bejaia ->  R.string.Province_bejaia.toString()
            R.id.Province_biskra ->  R.string.Province_biskra.toString()
            R.id.Province_bechar ->  R.string.Province_bechar.toString()
            R.id.Province_blida ->  R.string.Province_blida.toString()
            R.id.Province_bouira ->  R.string.Province_bouira.toString()


            R.id.Province_tamanrasset ->  R.string.Province_tamanrasset.toString()
            R.id.Province_tebessa ->  R.string.Province_tebessa.toString()
            R.id.Province_tlemcen ->  R.string.Province_tlemcen.toString()
            R.id.Province_tiaret ->  R.string.Province_tiaret.toString()
            R.id.Province_tizi_ouzou ->  R.string.Province_tizi_ouzou.toString()
            R.id.Province_alger ->  R.string.Province_alger.toString()
            R.id.Province_djelfa ->  R.string.Province_djelfa.toString()
            R.id.Province_jijel -> R.string.Province_jijel.toString()
            R.id.Province_setif ->  R.string.Province_setif.toString()
            R.id.Province_saida ->  R.string.Province_saida.toString()

            R.id.Province_skikda ->  R.string.Province_skikda.toString()
            R.id.Province_sidi_bel_abbes -> R.string.Province_sidi_bel_abbes.toString()
            R.id.Province_annaba ->R.string.Province_annaba.toString()
            R.id.Province_guelma -> R.string.Province_guelma.toString()
            R.id.Province_constantine -> R.string.Province_constantine.toString()
            R.id.Province_medea -> R.string.Province_medea.toString()
            R.id.Province_mostaganem -> R.string.Province_mostaganem.toString()
            R.id.Province_msila ->  R.string.Province_msila.toString()
            R.id.Province_mascara ->  R.string.Province_mascara.toString()
            R.id.Province_ouargla -> R.string.Province_ouargla.toString()

            R.id.Province_oran ->  R.string.Province_oran.toString()
            R.id.Province_elbayadh -> R.string.Province_elbayadh.toString()
            R.id.Province_illizi -> R.string.Province_illizi.toString()
            R.id.Province_bordj_bou_arreridj ->  R.string.Province_bordj_bou_arreridj.toString()
            R.id.Province_boumerdes ->  R.string.Province_boumerdes.toString()
            R.id.Province_el_tarf ->  R.string.Province_el_tarf.toString()
            R.id.Province_tindouf ->  R.string.Province_tindouf.toString()
            R.id.Province_tissemsilt ->  R.string.Province_tissemsilt.toString()
            R.id.Province_el_oued ->  R.string.Province_el_oued.toString()
            R.id.Province_khenchela ->  R.string.Province_khenchela.toString()

            R.id.Province_souk_ahras ->  R.string.Province_souk_ahras.toString()
            R.id.Province_tipaza -> R.string.Province_tipaza.toString()
            R.id.Province_mila -> R.string.Province_mila.toString()
            R.id.Province_ain_defla ->  R.string.Province_ain_defla.toString()
            R.id.Province_Naama -> R.string.Province_Naama.toString()
            R.id.Province_ain_temouchent -> R.string.Province_ain_temouchent.toString()
            R.id.Province_ghardaia -> R.string.Province_ghardaia.toString()
            R.id.Province_relizane -> R.string.Province_relizane.toString()
            R.id.Province_el_mghair -> R.string.Province_el_mghair.toString()
            R.id.Province_el_menia -> R.string.Province_el_menia.toString()

            R.id.Province_ouled_djellal -> R.string.Province_ouled_djellal.toString()
            R.id.Province_bordj_baji_mokhtar -> R.string.Province_bordj_baji_mokhtar.toString()
            R.id.Province_beni_abbes -> R.string.Province_beni_abbes.toString()
            R.id.Province_timimoun -> R.string.Province_timimoun.toString()
            R.id.Province_touggourt -> R.string.Province_touggourt.toString()
            R.id.Province_djanet -> R.string.Province_djanet.toString()
            R.id.Province_in_salah -> R.string.Province_in_salah.toString()
            R.id.Province_in_guezzam -> R.string.Province_in_guezzam.toString()

            else -> "Province"
        }

        binding.popMenuButton.text = province


        return true
    }



}