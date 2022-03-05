package com.example.smartpharm.views


import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.smartpharm.databinding.PharmacyDetailsFragmentBinding
import com.example.smartpharm.models.User
import com.google.gson.Gson

class PharmacyDetailsFragment : Fragment() {


    private lateinit var binding:PharmacyDetailsFragmentBinding

    private fun getData(): String?{
        val prefUser = activity?.getSharedPreferences("PharmacyProfile", Context.MODE_PRIVATE)
        return prefUser?.getString("pharmacyProfile","")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = PharmacyDetailsFragmentBinding.inflate(inflater, container, false)




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gson = Gson()
        val json :String = if(getData()!=null) getData()!! else ""
        val user : User? = gson.fromJson(json, User::class.java)

        if(user!=null){
            binding.nameDetailPharmacy.text = user.company
            binding.locationDetailPharmacy.text = user.locationUser
            binding.gmailDetailPharmacy.text = user.emailUser
            binding.FacebookAccountPharmacy.text = user.facebookAccount
            binding.InstagramPharmacy.text = user.instagramAccount
            binding.PhonePharmacy.text =user.phoneNumber

            binding.FacebookAccountPharmacy.setOnClickListener{
                val url  = "fb://page/${user.facebookAccount}"
                try {
                    openPage(requireContext(), url)
                }catch (e: ActivityNotFoundException){
                    val newUrl = url.replace("fb://","http:www.facebook.com/")
                    openPage(requireContext(), newUrl)
                }
            }
            binding.InstagramPharmacy.setOnClickListener{
                val url  = "http:www.instagram.com/${user.instagramAccount}"
                openPage(requireContext(), url)
            }
            binding.PhonePharmacy.setOnClickListener {

                    val tel = user.phoneNumber
                    val url = "tel:$tel"
                    try {
                        openPage(requireContext(), url)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(
                            context,
                            "You haven't phone application to make a call",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

            }
        }
    }

    private fun openPage(ctx: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        ctx.startActivity(intent)
    }



}