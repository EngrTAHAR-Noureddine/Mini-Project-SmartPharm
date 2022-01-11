package com.example.smartpharm.client.pharmacist_detail.main

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.smartpharm.R
import com.example.smartpharm.client.pharmacist_detail.details.PharmacyDetailsFragment
import com.example.smartpharm.medications_pharmacy.MedicationsListFragment
import com.example.smartpharm.databinding.PharmacistDetailFragmentBinding
import com.example.smartpharm.firebase.models.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class PharmacistDetailFragment : Fragment() {

    private lateinit var viewModel: PharmacistDetailViewModel
    private lateinit var binding: PharmacistDetailFragmentBinding

    private lateinit var demoCollectionAdapter: DemoCollectionAdapter
    private lateinit var viewPager: ViewPager2


    private fun getData(): String?{
        val prefUser = activity?.getSharedPreferences("PharmacyProfile", Context.MODE_PRIVATE)
        return prefUser?.getString("pharmacyProfile","")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.pharmacist_detail_fragment,container,false)



        val viewModelFactory = PharmacyDetailFragmentFactory(binding ,this.requireActivity())

        val pharmacyDetailViewModel = ViewModelProvider(this, viewModelFactory)[PharmacistDetailViewModel::class.java]

        val gson = Gson()
        val json :String = if(getData()!=null) getData()!! else ""
        val p : User? = gson.fromJson(json, User::class.java)

        // --- for Phone and Location Button ---
        binding.buttonPhone.setOnClickListener {
            val user :User? = p
            if(user != null){
                val tel = user.phoneNumber
                val url = Uri.parse("tel:$tel")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()))
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        activity,
                        "You haven't phone application to make a call",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.buttonMapLocation.setOnClickListener {
            val user : User? = p
            if(user != null){
                val latitude = 28.0339
                val longitude = 1.6596
                val url = Uri.parse("geo:$latitude,$longitude")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()))
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        activity,
                        "You haven't an application to find a location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        //------------------------------

        // ----- Photo -------------------

        if(p!=null) Picasso.with(context).load(p.photoUser).fit().centerCrop().into(binding.imagePharmacy)

        //----------------------------------


        binding.pharmacyDetailViewModel = pharmacyDetailViewModel
        binding.lifecycleOwner = this

        demoCollectionAdapter = DemoCollectionAdapter(this)


        viewPager = binding.pager
        viewPager.adapter = demoCollectionAdapter

        val tabLayout = binding.tabLayout

        TabLayoutMediator(tabLayout, viewPager) { tab, position -> when(position){
                1 -> tab.text = "Stock"
                else -> tab.text = "Detail"
        }

        }.attach()


        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        if(navBar != null){
            navBar.isVisible = false
        }

        return binding.root
    }



}

class DemoCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = when(position){
            1-> MedicationsListFragment()
            else -> PharmacyDetailsFragment()
        }
        return fragment
    }
}
