package com.example.smartpharm.client.demande_order

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.smartpharm.R
import com.example.smartpharm.controllers.FileController.destroyAllFiles
import com.example.smartpharm.controllers.FileController.listFile
import com.example.smartpharm.databinding.DemandeOrderFragmentBinding
import com.example.smartpharm.firebase.models.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import com.google.firebase.storage.StorageReference
import android.app.ProgressDialog
import com.example.smartpharm.client.demande_order.progressdialog.CustomProgressDialog
import com.example.smartpharm.client.demande_order.progressdialog.ProgressValue.updateProgress
import com.example.smartpharm.medications_pharmacy.addmedication.DialogAddMedicationFragment


class DemandeOrderViewModel(private val binding: DemandeOrderFragmentBinding,
                            private val context : FragmentActivity
) : ViewModel() {
     private val numberPhotos = MutableLiveData<Int?>()
     private val dialogBox : CustomProgressDialog = CustomProgressDialog()


    fun takePhoto(){
        numberPhotos.value = if(listFile.value!=null) listFile.value!!.size else 0
        if(numberPhotos.value!! <3){
            context.findNavController(R.id.myNavHostFragment).navigate(R.id.to_Camera_Fragment)
        }else{
            binding.floatingBtnCamera.isEnabled = false
            binding.floatingBtnCamera.isClickable = false
            binding.floatingBtnCamera.backgroundTintList = ColorStateList.valueOf(Color.LTGRAY)
        }
    }

    fun customResume(){
        numberPhotos.value = if(listFile.value!=null) listFile.value!!.size else 0
        if(numberPhotos.value!! >2){
            binding.floatingBtnCamera.isEnabled = false
            binding.floatingBtnCamera.isClickable = false
            binding.floatingBtnCamera.backgroundTintList = ColorStateList.valueOf(Color.LTGRAY)
        }
    }

    fun acceptOrder(){ //"PharmacyProfile"  - "pharmacyProfile"
        val gson = Gson()
        val stringPharmacy:String? = getData("PharmacyProfile", "pharmacyProfile")
        val stringUser:String? = getData("UserProfile", "userProfile")
        val json :String = if(stringPharmacy!=null) stringPharmacy!! else ""
        val json2 :String = if(stringUser!=null) stringUser!! else ""
        val pharmacy : User? = gson.fromJson(json, User::class.java)
        val user : User? = gson.fromJson(json2, User::class.java)

        val storage = Firebase.storage
        val storageRef = storage.reference


        if(listFile.value!=null){
            for ( item in listFile.value!!){
                var file = Uri.fromFile(item)
                val riversRef = storageRef.child("ImagesSmartPharm/${file.lastPathSegment}")
                var uploadTask = riversRef.putFile(file)
                Log.v("UploadFile", "I'm in upload btn")
                uploadTask.addOnProgressListener {
                    val progress :Double = (100.0 * it.bytesTransferred) / it.totalByteCount
                    Log.d("UploadFile", "Upload is $progress% done")
                    updateProgress(progress)
                    if(!dialogBox.isAdded){
                        dialogBox.isCancelable = false
                        dialogBox.showNow(context.supportFragmentManager,"Uploading")
                    }
                    //CustomProgressDialog().showsDialog = true



                }.addOnPausedListener {
                    Log.d("UploadFile", "Upload is paused")
                }.addOnFailureListener {

                    Log.v("UploadFile", "Failed Upload")
                    if(dialogBox.isAdded){dialogBox.dismiss()}
                    Toast.makeText(context,"Failed Upload", Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener {
                    Log.v("UploadFile", "Success Upload")
                    if(dialogBox.isAdded){dialogBox.dismiss()}
                    Toast.makeText(context,"Success Upload", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    private fun getData(file:String, string: String): String?{
        val prefUser = context.getSharedPreferences(file, Context.MODE_PRIVATE)
        return prefUser?.getString(string,"")
    }

    fun declineOrder(){
        binding.inputNote.text.clear()
        destroyAllFiles()
    }
}