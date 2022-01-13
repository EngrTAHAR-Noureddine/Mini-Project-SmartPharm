package com.example.smartpharm.client.demande_order

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.smartpharm.R
import com.example.smartpharm.controllers.FileController.destroyAllFiles
import com.example.smartpharm.controllers.FileController.listFile
import com.example.smartpharm.databinding.DemandeOrderFragmentBinding



class DemandeOrderViewModel(private val binding: DemandeOrderFragmentBinding,
                            private val context : FragmentActivity
) : ViewModel() {
     private val numberPhotos = MutableLiveData<Int?>()




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

    fun declineOrder(){
        binding.inputNote.text.clear()
        destroyAllFiles()
    }
}