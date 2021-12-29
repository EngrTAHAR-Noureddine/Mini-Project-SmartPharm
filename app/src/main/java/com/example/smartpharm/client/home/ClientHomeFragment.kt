package com.example.smartpharm.client.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartpharm.R

class ClientHomeFragment : Fragment() {

    // afficher list des pharmacists et fo9a dir PopupMenu ( ou input field ) et botton pour filtrer par ville

    companion object {
        fun newInstance() = ClientHomeFragment()
    }

    private lateinit var viewModel: ClientHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.client_home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClientHomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}