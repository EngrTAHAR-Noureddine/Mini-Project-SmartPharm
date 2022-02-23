package com.example.smartpharm.views

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.smartpharm.R
import com.example.smartpharm.databinding.DemandeOrderFragmentBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smartpharm.viewmodel_factories.DemandeOrderFragmentFactory
import com.example.smartpharm.viewmodels.DemandeOrderViewModel
import com.example.smartpharm.client.demande_order.GridImageAdapter
import com.example.smartpharm.controllers.FileController.destroyAllFiles
import com.example.smartpharm.controllers.FileController.imagesReader
import com.example.smartpharm.controllers.FileController.listFile

class DemandeOrderFragment : Fragment() {

    private lateinit var binding: DemandeOrderFragmentBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater,R.layout.demande_order_fragment,container,false)

        val viewModelFactory = DemandeOrderFragmentFactory(binding ,this.requireActivity())

        val demandeOrderViewModel = ViewModelProvider(this, viewModelFactory)[DemandeOrderViewModel::class.java]

        binding.demandeOrderViewModel = demandeOrderViewModel
        binding.lifecycleOwner = this

        imagesReader(requireContext())

        binding.GridRecycleView.layoutManager = GridLayoutManager(context, 3)

        listFile.observe(viewLifecycleOwner, {
               binding.GridRecycleView.adapter = GridImageAdapter(activity, it)
               Log.v("ObserverListFile", " this is observer")

                binding.floatingBtnCamera.isEnabled = !(listFile.value!=null && listFile.value!!.size >2)
                binding.floatingBtnCamera.isClickable = !(listFile.value!=null && listFile.value!!.size >2)
                binding.floatingBtnCamera.backgroundTintList = if(listFile.value!=null && listFile.value!!.size >2)
                                                                    ColorStateList.valueOf(Color.LTGRAY) else  ColorStateList.valueOf(Color.rgb(48,54,61))

        })

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        destroyAllFiles()
        Log.v("Destroy", "destroy files")
    }


}