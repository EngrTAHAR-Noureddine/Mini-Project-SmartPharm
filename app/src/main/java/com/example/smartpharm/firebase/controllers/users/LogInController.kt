package com.example.smartpharm.firebase.controllers.users

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.example.smartpharm.R
import com.example.smartpharm.client.ClientActivity
import com.example.smartpharm.firebase.controllers.UserController
import com.example.smartpharm.firebase.models.User
import com.example.smartpharm.pharmacist.PharmacistActivity
import com.google.gson.Gson

object LogInController {
    fun loginUser(email:String,password:String,context: FragmentActivity) {

        var privateUser: User?

        UserController.db.collection("User")
            .get()
            .addOnSuccessListener { querySnapshot ->run{
                if (!querySnapshot.isEmpty) {
                    var list:List<User>? = querySnapshot.toObjects(User::class.java)
                    list = list?.filter{ item -> (item.emailUser == email && item.passwordUser== password)}
                    privateUser = list?.get(0)

                    if(privateUser != null){

                        val pref = context.getSharedPreferences("TypeUserFile", Context.MODE_PRIVATE)
                        val editor : SharedPreferences.Editor = pref.edit()
                        editor.apply{
                            putString("typeUserFile",privateUser!!.typeUser)
                        }.apply()

                        val gson = Gson()
                        val prefUser = context.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
                        val editorUser : SharedPreferences.Editor = prefUser.edit()
                        val json = gson.toJson(privateUser)
                        editorUser.apply{
                            putString("userProfile",json)
                        }.apply()

                        if(privateUser!!.typeUser == "Pharmacy"){
                            val intent = Intent(context, PharmacistActivity::class.java)
                            context.startActivity(intent)
                            context.finish()
                        }
                        else{ //Client
                            val intent = Intent(context, ClientActivity::class.java)
                            context.startActivity(intent)
                            context.finish()
                        }

                    }else{
                        val progressBar = context.findViewById<ProgressBar>(R.id.progressBarLogin)
                        progressBar.isVisible = false
                        val buttonLogin = context.findViewById<Button>(R.id.buttonLogIn)
                        buttonLogin.isVisible = true
                        val text = "email or password is incorrect"
                        val duration = Toast.LENGTH_SHORT
                        val toast = Toast.makeText(context, text, duration)
                        toast.show()
                    }

                }
            }
            }
            .addOnFailureListener { exception ->run{
                val progressBar = context.findViewById<ProgressBar>(R.id.progressBarLogin)
                progressBar.isVisible = false
                val buttonLogin = context.findViewById<Button>(R.id.buttonLogIn)
                buttonLogin.isVisible = true
                Log.w("FIRESTORE", "Error getting documents $exception")
                val text = "Out Connexion"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }

            }
    }
}