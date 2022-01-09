package com.example.smartpharm.pharmacist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.smartpharm.R
import kotlinx.android.synthetic.main.activity_pharmacist.bottom_navigation

class PharmacistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacist)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myPharmacyNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        setupBottomNavMenu(navController)

    }

    private fun setupBottomNavMenu(navController: NavController) {
        bottom_navigation?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }


}