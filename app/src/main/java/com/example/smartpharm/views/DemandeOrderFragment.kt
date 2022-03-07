package com.example.smartpharm.views

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.smartpharm.databinding.DemandeOrderFragmentBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smartpharm.viewmodels.DemandeOrderViewModel
import com.example.smartpharm.adapters.GridImageAdapter
import com.example.smartpharm.controllers.FileController.destroyAllFiles
import com.example.smartpharm.controllers.FileController.imagesReader
import com.example.smartpharm.controllers.FileController.listFile

class DemandeOrderFragment : Fragment() {

    private lateinit var binding: DemandeOrderFragmentBinding
    private val demandeOrderViewModel : DemandeOrderViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DemandeOrderFragmentBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imagesReader(requireContext())

        binding.GridRecycleView.layoutManager = GridLayoutManager(context, 3)

        listFile.observe(viewLifecycleOwner) {
            binding.GridRecycleView.adapter = GridImageAdapter(activity, it)
            Log.v("ObserverListFile", " this is observer")
            binding.floatingBtnCamera.isEnabled =
                !(listFile.value != null && listFile.value!!.size > 2)
            binding.floatingBtnCamera.isClickable =
                !(listFile.value != null && listFile.value!!.size > 2)
            binding.floatingBtnCamera.backgroundTintList =
                if (listFile.value != null && listFile.value!!.size > 2)
                    ColorStateList.valueOf(Color.LTGRAY) else ColorStateList.valueOf(
                    Color.rgb(
                        48,
                        54,
                        61
                    )
                )
        }

        binding.buttonAccept.setOnClickListener{
            demandeOrderViewModel.acceptOrder(binding,requireActivity())
        }
        binding.buttonDecline.setOnClickListener {
            demandeOrderViewModel.declineOrder(requireActivity(),binding)
        }
        binding.floatingBtnCamera.setOnClickListener {
            demandeOrderViewModel.takePhoto(binding,requireActivity())
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        destroyAllFiles()
        Log.v("Destroy", "destroy files")
    }
    /*
    override fun onDestroyView() {
        super.onDestroyView()
        destroyAllFiles()
    }
    */


}