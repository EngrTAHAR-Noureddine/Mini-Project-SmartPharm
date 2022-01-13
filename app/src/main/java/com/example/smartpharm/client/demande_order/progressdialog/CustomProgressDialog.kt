package com.example.smartpharm.client.demande_order.progressdialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.smartpharm.R
import com.example.smartpharm.client.demande_order.progressdialog.ProgressValue.progress
import com.example.smartpharm.databinding.FragmentProgressDialogBinding


class CustomProgressDialog : DialogFragment() {

    private lateinit var binding : FragmentProgressDialogBinding
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_progress_dialog,container,false)
        progress.observe(viewLifecycleOwner,{
            binding.textUpload.text = "Upload...${String.format("%.2f", it)}"
        })
        return binding.root
    }




}