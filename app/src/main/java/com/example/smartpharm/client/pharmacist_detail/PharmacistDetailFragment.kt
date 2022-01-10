package com.example.smartpharm.client.pharmacist_detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.smartpharm.R
import com.example.smartpharm.client.pharmacist_detail.details.PharmacyDetailsFragment
import com.example.smartpharm.client.pharmacist_detail.medications.MedicationsListFragment
import com.example.smartpharm.database.smartDataBase
import com.example.smartpharm.databinding.PharmacistDetailFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator

class PharmacistDetailFragment : Fragment() {

    private lateinit var viewModel: PharmacistDetailViewModel
    private lateinit var binding: PharmacistDetailFragmentBinding

    private lateinit var demoCollectionAdapter: DemoCollectionAdapter
    private lateinit var viewPager: ViewPager2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.pharmacist_detail_fragment,container,false)

        val application = requireNotNull(this.activity).application
        val dataSource = smartDataBase.getInstance(application)?.UsersDao()!!

        val viewModelFactory = PharmacyDetailFragmentFactory(dataSource, binding ,this.requireActivity())

        val pharmacyDetailViewModel = ViewModelProvider(this, viewModelFactory)[PharmacistDetailViewModel::class.java]

        binding.pharmacyDetailViewModel = pharmacyDetailViewModel
        binding.lifecycleOwner = this

        demoCollectionAdapter = DemoCollectionAdapter(this)


        viewPager = binding.pager
        viewPager.adapter = demoCollectionAdapter

        val tabLayout = binding.tabLayout

        TabLayoutMediator(tabLayout, viewPager) { tab, position -> when(position){
                1 -> tab.text = "Stock"
                else -> tab.text = "Detail"
        }

        }.attach()


        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation);
        if(navBar != null){
            navBar.isVisible = false
        }

        return binding.root
    }



}

class DemoCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        val fragment = when(position){
            1-> MedicationsListFragment()
            else -> PharmacyDetailsFragment()
        }
        return fragment
    }
}

private const val ARG_OBJECT = "object"

// Instances of this class are fragments representing a single
// object in our collection.
class DemoObjectFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.medications_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val textView: TextView = view.findViewById(R.id.textViewTest)
            textView.text = getInt(ARG_OBJECT).toString()
        }
    }
}