package com.example.smartpharm.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smartpharm.databinding.FragmentFullscreenImageBinding
import com.squareup.picasso.Picasso
import java.io.File

class FullscreenImageFragment : Fragment() {





    private var fullscreenContent: View? = null

    private lateinit var _binding: FragmentFullscreenImageBinding


    private fun getData(file:String, string: String): String?{
        val prefUser = context?.getSharedPreferences(file, Context.MODE_PRIVATE)
        return prefUser?.getString(string,"")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFullscreenImageBinding.inflate(inflater,container,false)

        val json: String = getData("ImageOrder", "imageOrder") ?: ""
        val file = File(json)
        Picasso.with(context).load(file).fit().centerInside().into(_binding.fullscreenContent)

        return _binding.root

    }

}