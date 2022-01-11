package com.example.smartpharm.firebase.controllers.medications

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.example.smartpharm.firebase.DataBase
import com.example.smartpharm.firebase.models.Medication
import com.example.smartpharm.firebase.models.User

object MedicationController {

    var listMedications = MutableLiveData<List<Medication>?>()

    fun getMedicationOf(pharmacy:User?,context:FragmentActivity){
        DataBase.db.collection("Medication")
            .get()
            .addOnSuccessListener { querySnapshot ->run{
                if (!querySnapshot.isEmpty) {
                    var list:List<Medication>? = querySnapshot.toObjects(Medication::class.java)
                    listMedications.value = if(pharmacy!=null) list?.filter{ item -> (item.emailPharmacy == pharmacy.emailUser)} else null
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
    }


}