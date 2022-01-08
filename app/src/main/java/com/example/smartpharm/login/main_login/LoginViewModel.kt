package com.example.smartpharm.login.main_login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.request.SuccessResult
import com.example.smartpharm.client.ClientActivity
import com.example.smartpharm.database.users.UsersDao
import com.example.smartpharm.databinding.LoginFragmentBinding
import com.example.smartpharm.model.User
import com.example.smartpharm.pharmacist.PharmacistActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.gson.Gson




class LoginViewModel(private val userDatabase: UsersDao,private val binding: LoginFragmentBinding,
                     private val context : FragmentActivity) : ViewModel() {


    private var _email = MutableLiveData<String>()
    private var _password  = MutableLiveData<String>()
    var users = userDatabase.getAllUsers()
    private var pref : SharedPreferences? = null

    val email: LiveData<String>
        get() = _email
    val password: LiveData<String>
        get() = _password


    init {
        _email.value = ""
        _password.value = ""
        this.pref = context?.getSharedPreferences("firstTime", Context.MODE_PRIVATE)
        if(getData() != null && getData()!! ){
            initTableUsers()
            saveData(false)
        }
        initializeUsers()

    }

    private fun saveData(firstTime:Boolean){
        val editor : SharedPreferences.Editor? = this.pref?.edit()
        editor?.apply{
            putBoolean("firstTime",firstTime)
        }?.apply()
    }
    private fun getData(): Boolean? {
        return this.pref?.getBoolean("firstTime",true)
    }

    private fun initTableUsers(){
        viewModelScope.launch{
            val user1 = User()
            user1.name = "user 01"
            user1.emailUser = "client@client.com"
            user1.facebookAccount = "shazilparacha341"
            user1.instagramAccount = "shazilparacha341"
            user1.photoUser = getBitmap(context)
            user1.locationUser = "location"
            user1.phoneNumber = "+2133456789"
            user1.typeUser = "Client"
            user1.passwordUser = "root"
            insert(user1)
        }

        viewModelScope.launch {
            val user2 = User()
            user2.name = "user 02"
            user2.emailUser = "pharmacist@pharmacist.com"
            user2.facebookAccount = "shazilparacha341"
            user2.instagramAccount = "shazilparacha341"
            user2.locationUser = "location"
            user2.photoUser = getBitmap(context)
            user2.phoneNumber = "+2133456789"
            user2.typeUser = "Pharmacist"
            user2.passwordUser = "root"
            insert(user2)
        }
    }

    private fun initializeUsers() {
        viewModelScope.launch {
            users = userDatabase.getAllUsers()
        }
    }

    private suspend fun insert(operationSyntax: User) {
        withContext(Dispatchers.IO) {
            userDatabase.insertUser(operationSyntax)
        }
    }


    fun onCLickLogIn(){

        _email.value = if(binding.inputEmail.text != null) binding.inputEmail.text.toString() else "none"
        _password.value = if(binding.inputPassword.text != null) binding.inputPassword.text.toString() else "none"

        if(binding.inputEmail.text != null && binding.inputPassword.text != null && _email.value!!.isNotEmpty() && _password.value!!.isNotEmpty()){
            var user : User? = null


            if(users != null && users.value != null && users.value!!.isNotEmpty()){
                for (item in users.value!!) {
                    if(item.emailUser == _email.value && item.passwordUser == _password.value){
                        user = item
                    }
                }
                if(user != null){

                    val pref = context.getSharedPreferences("TypeUserFile", Context.MODE_PRIVATE)
                    val editor : SharedPreferences.Editor = pref.edit()
                    editor.apply{
                        putString("typeUser",user.typeUser)
                    }.apply()

                    val gson = Gson()
                    val prefUser = context.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
                    val editorUser : SharedPreferences.Editor = prefUser.edit()
                    val json = gson.toJson(user)
                    editorUser.apply{
                        putString("userProfile",json)
                    }.apply()
                    /*
                    to retrieve :
                                Gson gson = new Gson();
                                String json = mPrefs.getString("MyObject", "");
                                MyObject obj = gson.fromJson(json, MyObject.class);
                    * */

                    if(user.typeUser == "Pharmacist"){
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
                    val text = "User couldn't find : ${_email.value} && ${_password.value}"
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText(context, text, duration)
                    toast.show()
                }

            }else{
                val text = "User couldn't find : users ${users.value?.size}"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }


        }else{
            val text = "User couldn't find : _email and _password null"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(context, text, duration)
            toast.show()
        }

    }


    private suspend fun getBitmap(context: Context): Bitmap {
        val loading = coil.ImageLoader(context)
        val request = coil.request.ImageRequest.Builder(context)
            .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

}