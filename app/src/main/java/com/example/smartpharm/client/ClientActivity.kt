package com.example.smartpharm.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.smartpharm.R
import com.example.smartpharm.Settings.SettingsFragment
import com.example.smartpharm.client.home.ClientHomeFragment
import com.example.smartpharm.client.orders.ListOrdersFragment
import kotlinx.android.synthetic.main.activity_client.*



class ClientActivity : AppCompatActivity() {
    private val clientHomeFragment = ClientHomeFragment()
    private val listOrdersFragment = ListOrdersFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        replaceFragment(clientHomeFragment)

        bottom_navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> replaceFragment(clientHomeFragment)
                R.id.ic_list_orders -> replaceFragment(listOrdersFragment)
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