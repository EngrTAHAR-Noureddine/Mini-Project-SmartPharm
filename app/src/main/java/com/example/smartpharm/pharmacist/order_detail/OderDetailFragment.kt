package com.example.smartpharm.pharmacist.order_detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartpharm.R

class OderDetailFragment : Fragment() {

    // detail sur Oder : afficher photo , nome client ,address cleint ..., ( les info de client )

    companion object {
        fun newInstance() = OderDetailFragment()
    }

    private lateinit var viewModel: OderDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.oder_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OderDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}