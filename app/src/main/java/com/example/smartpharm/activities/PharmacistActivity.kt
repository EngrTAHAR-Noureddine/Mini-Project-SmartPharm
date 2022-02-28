package com.example.smartpharm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.smartpharm.R
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView

class PharmacistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacist)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myPharmacyNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = findViewById<BubbleNavigationConstraintView>(R.id.bottom_navigation_ph)


        bottomNav.setUpWithNavController(navController)

    }

    private fun BubbleNavigationConstraintView.setUpWithNavController(navController: NavController){

        val currentPosition = when (navController.currentDestination?.id){

            R.id.destination_Pharmacy_Medications_List -> { 1 }
            R.id.destination_Pharmacy_Settings -> { 2 }
            else -> { 0 }
        }

        this.setCurrentActiveItem(currentPosition)
        this.setNavigationChangeListener { view, position ->
            this.setCurrentActiveItem(position)
            when(view.id){

                R.id.destination_Pharmacy_Medications_List -> {
                    navController.navigate(R.id.destination_Pharmacy_Medications_List)
                }

                R.id.destination_Pharmacy_Settings -> {
                    navController.navigate(R.id.destination_Pharmacy_Settings)
                }

                else -> {
                    navController.navigate(R.id.destination_Pharmacy_Home)
                }
            }
        }

    }


}