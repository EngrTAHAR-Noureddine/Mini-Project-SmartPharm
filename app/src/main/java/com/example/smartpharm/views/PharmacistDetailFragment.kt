package com.example.smartpharm.views

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.smartpharm.R
import com.example.smartpharm.activities.LoginActivity
import com.example.smartpharm.controllers.FileController
import com.example.smartpharm.databinding.PharmacistDetailFragmentBinding
import com.example.smartpharm.models.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation


class PharmacistDetailFragment : Fragment() {

    private lateinit var binding: PharmacistDetailFragmentBinding

    private lateinit var demoCollectionAdapter: DemoCollectionAdapter
    private lateinit var viewPager: ViewPager2
    private var user:User? = null

    private fun getData(): String?{
        val prefUser = activity?.getSharedPreferences("PharmacyProfile", Context.MODE_PRIVATE)
        return prefUser?.getString("pharmacyProfile","")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = PharmacistDetailFragmentBinding.inflate(inflater,container,false)
        val gson = Gson()
        val json :String = if(getData()!=null) getData()!! else ""
        user = gson.fromJson(json, User::class.java)


        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        if(navBar != null) navBar.isVisible = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonPhone.setOnClickListener{
            if(user != null){
                val tel = user!!.phoneNumber
                val url = Uri.parse("tel:$tel")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()))
                try {
                    requireActivity().startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        context,
                        "You haven't phone application to make a call",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.buttonMapLocation.setOnClickListener {
            if(user != null){
                val latitude = 28.0339
                val longitude = 1.6596
                val url = Uri.parse("geo:$latitude,$longitude")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()))
                try {
                    requireActivity().startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        context,
                        "You haven't an application to find a location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.ButtonGoOrder.setOnClickListener {
            val pref =  requireActivity().getSharedPreferences("TypeUserFile", Context.MODE_PRIVATE)
            val typeUser = pref?.getString("typeUserFile", null)
            if(typeUser.isNullOrEmpty()){
                val intent = Intent(context, LoginActivity::class.java)
                requireActivity().startActivity(intent)
                requireActivity().finish()
            }else {
                FileController.emptyDir( requireActivity())
                requireActivity().findNavController(R.id.myNavHostFragment).navigate(R.id.to_Client_Order)
            }
        }


        // ----- Photo -------------------

        if(user!=null) Picasso.with(context).load(user!!.photoUser).transform( CircleTransform()).into(binding.imagePharmacy)

        //----------------------------------

        demoCollectionAdapter = DemoCollectionAdapter(this)


        viewPager = binding.pager
        viewPager.adapter = demoCollectionAdapter


        val tabLayout = binding.tabLayout

        TabLayoutMediator(tabLayout, viewPager) { tab, position -> when(position){
            1 -> tab.text = "Stock"
            else -> tab.text = "Detail"
        }

        }.attach()
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


}

class DemoCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = when(position){
            1-> MedicationsListFragment()
            else -> PharmacyDetailsFragment()
        }
        return fragment
    }
}
