package com.example.smartpharm.controllers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.smartpharm.database.DataBase
import com.example.smartpharm.database.MedicationDao
import com.example.smartpharm.database.OffLineDB
import com.example.smartpharm.models.*
import java.util.*

object MedicationController {

    var listMedications = MutableLiveData<MutableList<Medication>?>()
    var listMedicationsUser = MutableLiveData<MutableList<MyMedications>?>()
    var  medicationList : MutableList<Medication>? = listOf(
        Medication(
            idMedication = "01", nameMedication = "Aspirine",
            emailPharmacy = "root@root.com"
        ),
        Medication(
            idMedication = "02", nameMedication = "Doliprane",
            emailPharmacy = "root@root.com"
        ),
        Medication(
            idMedication = "03", nameMedication = "Paracitamol",
            emailPharmacy = "root@root.com"
        ),
        Medication(
            idMedication = "04", nameMedication = "Rapidius",
            emailPharmacy = "root@root.com"
        )
    ) as MutableList<Medication>?


    var  medicationUSERList : MutableList<MyMedications>? = listOf(
        MyMedications(
            idMedication = "01", idUser = "01",
            Days = 12, description = "After", Dinner = mapOf("hour" to 20, "minute" to 15) as MutableMap,
            first_day = mapOf("month" to 11, "day" to 15, "year" to 2021) as MutableMap,
            Launch = mapOf("hour" to 12, "minute" to 15) as MutableMap, Name = "Doliprane",
            photo = "https://www.pharma-gdd.com/cache/product_show/doliprane-500-mg-16-comprimes.jpg.webp"

        ),
        MyMedications(
            idMedication = "01", idUser = "01",
            Days = 12, description = "After", Dinner = mapOf("hour" to 20, "minute" to 15) as MutableMap,
            first_day = mapOf("month" to 11, "day" to 15, "year" to 2021) as MutableMap,
            Launch = mapOf("hour" to 12, "minute" to 15) as MutableMap, Name = "Rapidus",
            photo = "https://www.elkendi.com/Application/upload/402387834.jpg"

        ),
    ) as MutableList<MyMedications>?

    private lateinit var medicationDao: MedicationDao

    private fun init(context: Context){
        medicationDao = OffLineDB.getInstance(context)?.MedicationDao()!!
    }

    private fun deleteMedicationOfClient(med: MyMedications){
        DataBase.db.collection("MyMedications").document(med.idMedication)
            .delete()
            .addOnSuccessListener {
                Log.d("MED", "DocumentSnapshot successfully!")
            }
            .addOnFailureListener {  Log.d("MED", "DocumentSnapshot Failed!") }
    }

    private fun updateFBFromDB(context: Context){
        init(context)
       var medications : LiveData<List<MedicationDB>> = medicationDao.getAllMedications()


        if(! medicationDao.getAllMedications().value.isNullOrEmpty()){
            for (medDB in medications.value!!){
                var med = ConverterMedicationClasses.toMyMedication(medDB)
                if(med.Days <=0){
                    deleteMedicationOfClient(med)
                }else{
                    DataBase.db.collection("MyMedications").document(med.idMedication)
                        .update("Dinner", med.Dinner , "Launch",med.Launch,"Days",med.Days)
                        .addOnSuccessListener {
                            Log.d("MED", "DocumentSnapshot successfully!")
                        }
                        .addOnFailureListener {  Log.d("MED", "DocumentSnapshot Failed!") }
                }
            }
        }
    }

    private fun insertAllMedications(listMedication:List<MyMedications>){
        if(!listMedication.isNullOrEmpty()){
            medicationDao.clear()
            for (med in listMedication){
                var medDB = ConverterMedicationClasses.toMedicationDB(med)
                medicationDao.insert(medDB)
            }
        }
    }

    fun getMedicationOfClient(user: User?, context:Context){

        listMedicationsUser.value = medicationUSERList

        /*
        updateFBFromDB(context)

        DataBase.db.collection("MyMedications")
            .get()
            .addOnSuccessListener { querySnapshot ->run{


                if (!querySnapshot.isEmpty) {

                    var list:MutableList<MyMedications>? = querySnapshot.toObjects(MyMedications::class.java)
                    listMedicationsUser.value = if(user!=null) list?.filter{ item -> (item.idUser == user.idUser)} as MutableList<MyMedications>? else null
                    listMedicationsUser.value?.let { insertAllMedications(it) }
                }else{
                    medicationDao.clear()
                    val text = "The Client hasn't the medications"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(context, text, duration)
                    toast.show()
                }
            }
            }
            .addOnFailureListener { exception ->run{
                 val listMedDB = medicationDao.getAllMedications().value as MutableList<MedicationDB>?
                if(!listMedDB.isNullOrEmpty()){
                    var list = mutableListOf<MyMedications>()
                    listMedDB.forEach {
                        list.add(ConverterMedicationClasses.toMyMedication(it))
                    }
                    listMedicationsUser.value = list
                }else{
                    listMedicationsUser.value = null
                }
                Log.w("FIRESTORE", "Error getting documents $exception")
                val text = "Out Connexion"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }
            }

         */
    }

    fun updateFirstDays(med : MyMedications,context: Context){
        init(context)
        DataBase.db.collection("MyMedications").document(med.idMedication)
            .update("Days", med.Days , "first_day", med.first_day)
            .addOnSuccessListener {
                Log.d("MED", "DocumentSnapshot successfully!")
                medicationDao.update(ConverterMedicationClasses.toMedicationDB(med))
            }
            .addOnFailureListener {
                Log.d("MED", "DocumentSnapshot Failed!")
                medicationDao.update(ConverterMedicationClasses.toMedicationDB(med))
            }
    }

    fun updateDate(med : MyMedications, field:String,context:Context){
        init(context)
        val change : MutableMap<String, Int>? = if(field == "Dinner") med.Dinner else med.Launch

        DataBase.db.collection("MyMedications").document(med.idMedication)
            .update(field, change)
            .addOnSuccessListener {
                Log.d("MED", "DocumentSnapshot successfully!")
                medicationDao.update(ConverterMedicationClasses.toMedicationDB(med))
            }
            .addOnFailureListener {
                Log.d("MED", "DocumentSnapshot Failed!")
                medicationDao.update(ConverterMedicationClasses.toMedicationDB(med))
            }
    }




    fun getMedicationOf(pharmacy: User?, context:FragmentActivity){
        listMedications.value = medicationList
        /*
        DataBase.db.collection("Medication")
            .get()
            .addOnSuccessListener { querySnapshot ->run{
                if (!querySnapshot.isEmpty) {
                    var list:MutableList<Medication>? = querySnapshot.toObjects(Medication::class.java)
                    listMedications.value = if(pharmacy!=null) list?.filter{ item -> (item.emailPharmacy == pharmacy.emailUser)} as MutableList<Medication>? else null
                }else{
                    val text = "The Pharmacy hasn't the medications"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(context, text, duration)
                    toast.show()
                }
            }
            }
            .addOnFailureListener { exception ->run{
                Log.w("FIRESTORE", "Error getting documents $exception")
                val text = "Out Connexion"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }
            }
        */
    }

    fun postMedication(medication: Medication, context: Context){
            DataBase.db.collection("Medication").document(UUID.randomUUID().toString())
                .set(medication)
                .addOnSuccessListener {
                    var list :MutableList<Medication>?  = listMedications.value
                    list?.add(medication)
                    listMedications.value = list

                    Toast.makeText(context, "Success Upload", Toast.LENGTH_SHORT).show() }
                .addOnFailureListener { Toast.makeText(context, "Failed Upload", Toast.LENGTH_SHORT).show()}
    }

    fun deleteMedication(medication: Medication, context: Context){
        DataBase.db.collection("Medication").document(medication.idMedication)
            .delete()
            .addOnSuccessListener {
                Log.d("FIRESTORE", "DocumentSnapshot successfully deleted!")
                Toast.makeText(context, "successfully deleted!", Toast.LENGTH_SHORT).show()
                val list =if(listMedications.value!=null) listMedications.value!!.filter { item: Medication -> item.idMedication!= medication.idMedication}else null
                listMedications.value = list as MutableList<Medication>?
            }
            .addOnFailureListener {  Toast.makeText(context, "Delete Failed!", Toast.LENGTH_SHORT).show() }
    }

    fun searchMedication(word:String?, pharmacy: User?, context:FragmentActivity){
        if(!word.isNullOrEmpty()) {
            val list = listMedications.value?.filter { item: Medication ->
                item.nameMedication.lowercase(Locale.ROOT).contains(word.lowercase(Locale.ROOT))
            }
            listMedications.value = list as MutableList<Medication>?
        }
        else{
            getMedicationOf(pharmacy,context)
        }
    }
}