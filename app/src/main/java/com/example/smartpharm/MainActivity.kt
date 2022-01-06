package com.example.smartpharm
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.smartpharm.client.ClientActivity
import com.example.smartpharm.login.LoginActivity
import com.example.smartpharm.pharmacist.PharmacistActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = getSharedPreferences("TypeUserFile", Context.MODE_PRIVATE)
        val typeUser = pref.getString("typeUser", null)

        Log.d("typeUser", typeUser.toString())

        if (typeUser!=null && typeUser == "Client") {
            intent = Intent(applicationContext, ClientActivity::class.java)
            startActivity(intent)
            finish()
        }else if (typeUser!=null && typeUser == "Pharmacist"){
            intent = Intent(applicationContext, PharmacistActivity::class.java)
            startActivity(intent)
            finish()
        }
        else {
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}