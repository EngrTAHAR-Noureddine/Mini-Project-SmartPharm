package com.example.smartpharm.client.pharmacist_detail.main

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.smartpharm.R
import com.example.smartpharm.client.pharmacist_detail.details.PharmacyDetailsFragment
import com.example.smartpharm.databinding.PharmacistDetailFragmentBinding
import com.example.smartpharm.firebase.models.User
import com.example.smartpharm.medications_pharmacy.MedicationsListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation


class PharmacistDetailFragment : Fragment() {

    private lateinit var viewModel: PharmacistDetailViewModel
    private lateinit var binding: PharmacistDetailFragmentBinding

    private lateinit var demoCollectionAdapter: DemoCollectionAdapter
    private lateinit var viewPager: ViewPager2


    private fun getData(): String?{
        val prefUser = activity?.getSharedPreferences("PharmacyProfile", Context.MODE_PRIVATE)
        return prefUser?.getString("pharmacyProfile","")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.pharmacist_detail_fragment,container,false)

        val gson = Gson()
        val json :String = if(getData()!=null) getData()!! else ""
        val p : User? = gson.fromJson(json, User::class.java)

        val viewModelFactory = PharmacyDetailFragmentFactory(binding ,this.requireActivity(),p)

        val pharmacyDetailViewModel = ViewModelProvider(this, viewModelFactory)[PharmacistDetailViewModel::class.java]

        binding.pharmacyDetailViewModel = pharmacyDetailViewModel
        binding.lifecycleOwner = this

        // ----- Photo -------------------

        if(p!=null) Picasso.with(context).load(p.photoUser).transform( CircleTransform()).into(binding.imagePharmacy)

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


        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        if(navBar != null){
            navBar.isVisible = false
        }

        return binding.root
    }

    class CircleTransform : Transformation {
        override fun transform(source: Bitmap): Bitmap {
            val size = Math.min(source.width, source.height)
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
            paint.setShader(shader)
            paint.setAntiAlias(true)
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
