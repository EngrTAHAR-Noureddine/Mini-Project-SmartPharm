package com.example.smartpharm.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartpharm.databinding.SettingsFragmentBinding
import com.example.smartpharm.models.User
import com.example.smartpharm.activities.LoginActivity
import com.example.smartpharm.adapters.SettingRecycleViewAdapter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

class SettingsFragment : Fragment() {
    private lateinit var binding : SettingsFragmentBinding
    private var user:User?=null

    private fun getDataUser(): String?{
        val prefUser = activity?.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        return prefUser?.getString("userProfile","")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val gson = Gson()
        val json2 :String = if(getDataUser()!=null) getDataUser()!! else ""
        user = gson.fromJson(json2, User::class.java)

        val pref = context?.getSharedPreferences("TypeUserFile", Context.MODE_PRIVATE)
        val typeUser = pref?.getString("typeUserFile", null)
        if(typeUser.isNullOrEmpty()){
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding = SettingsFragmentBinding.inflate(inflater,container,false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(user!=null) {
            Picasso.with(context).load(user!!.photoUser).transform(CircleTransform()).into(binding.PhotoUser)
            this.binding.recycleViewSetting.layoutManager = LinearLayoutManager(activity)
            this.binding.recycleViewSetting.adapter = SettingRecycleViewAdapter(activity, user!!)
        }
        binding.buttonLogOut.setOnClickListener{
            val pref = activity?.getSharedPreferences("TypeUserFile", Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor? = pref?.edit()
            editor?.apply{
                putString("typeUserFile",null)
            }?.apply()
            val prefUser = activity?.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
            val editorUser : SharedPreferences.Editor? = prefUser?.edit()
            editorUser?.apply{
                putString("userProfile","")
            }?.apply()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    class CircleTransform : Transformation {
        override fun transform(source: Bitmap): Bitmap {
            val size = source.width.coerceAtMost(source.height)
            val x = (source.width - size) / 2
            val y = (source.height - size) / 2
            val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
            if (squaredBitmap != source) {
                source.recycle()
            }
            val bitmap = Bitmap.createBitmap(size, size, source.config)
            val canvas = Canvas(bitmap)
            val paint = Paint()
            val shader = BitmapShader(
                squaredBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
            )
            paint.shader = shader
            paint.isAntiAlias = true
            val r = size / 2f
            canvas.drawCircle(r, r, r, paint)
            squaredBitmap.recycle()
            return bitmap
        }

        override fun key(): String {
            return "circle"
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        user = null
    }



}