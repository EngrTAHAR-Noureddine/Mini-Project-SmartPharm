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
    var pharmaciesList : List<User> = listOf(
        User(idUser = "01", name = "Pharmacy 01",
            emailUser = "root@root.com", company = "El HANA",
            facebookAccount = "fbPharmacy01", instagramAccount = "instaPharmacy01",
            passwordUser = "root", photoUser = "https://img.freepik.com/free-vector/cartoon-illustration-with-pharmacy-building-cartoon-vector-people-illustration-flat-pharmacy_194782-610.jpg?w=2000",
            locationUser = "EL Harrache - Algiers", phoneNumber = "0234567786",
            typeUser = "Pharmacy", coordinate = mapOf("latitude" to 23.8 , "longitude" to 23.9 )),
        User(idUser = "02", name = "Pharmacy 02",
            emailUser = "root@root.com", company = "El BAHDJA",
            facebookAccount = "fbPharmacy02", instagramAccount = "instaPharmacy02",
            passwordUser = "root", photoUser = "https://img.freepik.com/free-vector/cartoon-illustration-with-pharmacy-building-cartoon-vector-people-illustration-flat-pharmacy_194782-610.jpg?w=2000",
            locationUser = "EL Harrache - Algiers", phoneNumber = "0234563433",
            typeUser = "Pharmacy", coordinate = mapOf("latitude" to 23.8 , "longitude" to 23.9 )),
        User(idUser = "03", name = "Pharmacy 03",
            emailUser = "root@root.com", company = "El WAFA",
            facebookAccount = "fbPharmacy03", instagramAccount = "instaPharmacy03",
            passwordUser = "root", photoUser = "https://img.freepik.com/free-vector/cartoon-illustration-with-pharmacy-building-cartoon-vector-people-illustration-flat-pharmacy_194782-610.jpg?w=2000",
            locationUser = "EL Harrache - Algiers", phoneNumber = "02345343786",
            typeUser = "Pharmacy", coordinate = mapOf("latitude" to 23.8 , "longitude" to 23.9 )),
        User(idUser = "04", name = "Pharmacy 04",
            emailUser = "root@root.com", company = "NOUR",
            facebookAccount = "fbPharmacy04", instagramAccount = "instaPharmacy04",
            passwordUser = "root", photoUser = "https://img.freepik.com/free-vector/cartoon-illustration-with-pharmacy-building-cartoon-vector-people-illustration-flat-pharmacy_194782-610.jpg?w=2000",
            locationUser = "EL Harrache - Algiers", phoneNumber = "02343437786",
            typeUser = "Pharmacy", coordinate = mapOf("latitude" to 23.8 , "longitude" to 23.9 )),
        User(idUser = "05", name = "Pharmacy 05",
            emailUser = "root@root.com", company = "Pharmacie el-madina",
            facebookAccount = "fbPharmacy05", instagramAccount = "instaPharmacy05",
            passwordUser = "root", photoUser = "https://img.freepik.com/free-vector/cartoon-illustration-with-pharmacy-building-cartoon-vector-people-illustration-flat-pharmacy_194782-610.jpg?w=2000",
            locationUser = "EL Harrache - Algiers", phoneNumber = "0234567786",
            typeUser = "Pharmacy", coordinate = mapOf("latitude" to 23.8 , "longitude" to 23.9 )),
        User(idUser = "06", name = "Pharmacy 06",
            emailUser = "root@root.com", company = "El-CHIFA",
            facebookAccount = "fbPharmacy06", instagramAccount = "instaPharmacy06",
            passwordUser = "root", photoUser = "https://img.freepik.com/free-vector/cartoon-illustration-with-pharmacy-building-cartoon-vector-people-illustration-flat-pharmacy_194782-610.jpg?w=2000",
            locationUser = "EL Harrache - Algiers", phoneNumber = "0234568886",
            typeUser = "Pharmacy", coordinate = mapOf("latitude" to 23.8 , "longitude" to 23.9 ))
    )

    fun getListPharmacies(context: Context){

        if(listPharmacies.value.isNullOrEmpty()) {
            /*
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
            */
            listPharmacies.value = pharmaciesList
        }



    }

    fun updateClient( user:User ,change:String, field:String){
        DataBase.db.collection("User").document(user.idUser)
            .update(field, change)
            .addOnSuccessListener {
                Log.d("FIRESTORE", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener {  Log.d("FIRESTORE", "DocumentSnapshot failed updated!")}
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