package com.example.smartpharm.controllers



import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.example.smartpharm.database.DataBase
import com.example.smartpharm.models.User
import java.util.*


object ClientController {

    var listPharmacies = MutableLiveData<List<User>?>()

    fun getListPharmacies(context: Context){
        if(listPharmacies.value.isNullOrEmpty()) {
            DataBase.db.collection("User")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    run {
                        if (!querySnapshot.isEmpty) {
                            var list: List<User>? = querySnapshot.toObjects(User::class.java)
                            listPharmacies.value =
                                list?.filter { item -> (item.typeUser == "Pharmacy") }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    run {
                        Log.w("FIRESTORE", "Error getting documents $exception")
                        val text = "Out Connexion"
                        val duration = Toast.LENGTH_SHORT
                        val toast = Toast.makeText(context, text, duration)
                        toast.show()
                    }

                }
        }

    }

    fun searchPharmacy(word:String?,context: FragmentActivity){


        if(!word.isNullOrEmpty() && !listPharmacies.value.isNullOrEmpty()) {
            val list = listPharmacies.value?.filter { item: User ->item.company.lowercase(Locale.ROOT).contains(word.lowercase(
                Locale.ROOT))
            }
            listPharmacies.value = list as MutableList<User>?
        }
        else{

            getListPharmacies(context)
        }
    }


    fun searchPharmacyByProvince(province:String?,context: FragmentActivity){

        Log.v("PRO","$province")
        if(!province.isNullOrEmpty() && !listPharmacies.value.isNullOrEmpty() && province!="Wilaya") {
            val list = listPharmacies.value?.filter { item: User ->item.locationUser.lowercase(Locale.ROOT).contains(province.lowercase(
                Locale.ROOT))
            }
            listPharmacies.value = list as MutableList<User>?
        }
        else{

            getListPharmacies(context)
        }
    }


    fun onCleared(){
        listPharmacies.value = null
    }



}