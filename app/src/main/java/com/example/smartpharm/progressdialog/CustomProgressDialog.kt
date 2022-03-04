package com.example.smartpharm.progressdialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.smartpharm.progressdialog.ProgressValue.progress
import com.example.smartpharm.databinding.FragmentProgressDialogBinding


class CustomProgressDialog : DialogFragment() {

    private lateinit var binding : FragmentProgressDialogBinding
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProgressDialogBinding.inflate(inflater,container,false)
        progress.observe(viewLifecycleOwner) {
            binding.textUpload.text = "Upload...${String.format("%.2f", it)}"
        }
        return binding.root
    }




}