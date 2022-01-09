package com.example.smartpharm.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.smartpharm.R
import com.example.smartpharm.Settings.SettingsFragment
import com.example.smartpharm.client.home.ClientHomeFragment
import com.example.smartpharm.client.orders.ListOrdersFragment
import kotlinx.android.synthetic.main.activity_client.*



class ClientActivity : AppCompatActivity() {
    //private val clientHomeFragment = ClientHomeFragment()
    //private val listOrdersFragment = ListOrdersFragment()
    //private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

       // val navController = Navigation.findNavController(this, R.id.myNavHostFragment)
        setupBottomNavMenu(navController)


        /*
        replaceFragment(clientHomeFragment)

        bottom_navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> replaceFragment(clientHomeFragment)
                R.id.ic_list_orders -> replaceFragment(listOrdersFragment)
                R.id.ic_setting -> replaceFragment(settingsFragment)
            }
            true
        }
        */

    }
    private fun setupBottomNavMenu(navController: NavController) {
        bottom_navigation?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    /*
    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()
    }
    */
}