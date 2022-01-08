package com.example.smartpharm.pharmacist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.smartpharm.R
import com.example.smartpharm.Settings.SettingsFragment
import com.example.smartpharm.pharmacist.home.PharmacistHomeFragment
import com.example.smartpharm.pharmacist.medication.MedicationListFragment
import kotlinx.android.synthetic.main.activity_pharmacist.bottom_navigation

class PharmacistActivity : AppCompatActivity() {

    private val pharmacistHomeFragment = PharmacistHomeFragment()
    private val medicationListFragment = MedicationListFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacist)
        replaceFragment(pharmacistHomeFragment)

        bottom_navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> replaceFragment(pharmacistHomeFragment)
                R.id.ic_list_orders -> replaceFragment(medicationListFragment)
                R.id.ic_setting -> replaceFragment(settingsFragment)
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()
    }
}