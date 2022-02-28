package com.example.smartpharm.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smartpharm.R
import com.example.smartpharm.adapters.GridImageAdapter
import com.example.smartpharm.controllers.FileController.destroyAllFiles
import com.example.smartpharm.controllers.FileController.listFile
import com.example.smartpharm.controllers.FileController.returnPhotos
import com.example.smartpharm.databinding.OderDetailFragmentBinding
import com.example.smartpharm.controllers.OrderController.listState
import com.example.smartpharm.models.Order
import com.example.smartpharm.models.User
import com.example.smartpharm.viewmodels.OderDetailViewModel
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation


class OderDetailFragment : Fragment() {


    private val viewModel: OderDetailViewModel by activityViewModels()
    private lateinit var mainBinding: OderDetailFragmentBinding
    private var type:String = ""
    private var order:Order? = null
    private var user:User? = null

    private fun getData(file:String, string: String): String?{
        val prefUser = context?.getSharedPreferences(file, Context.MODE_PRIVATE)
        return prefUser?.getString(string,"")
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mainBinding = OderDetailFragmentBinding.inflate(inflater,container,false)

        val gson = Gson()
        val json1: String = getData("Order", "orderDetail") ?: ""
        val json2: String = getData("UserProfile", "userProfile") ?: ""
        order = gson.fromJson(json1, Order::class.java)
        user = gson.fromJson(json2, User::class.java)


        if(user !=null) type = user!!.typeUser


        mainBinding.GridRecycleView.layoutManager = GridLayoutManager(context, 3)


        val navBar = activity?.findViewById<BubbleNavigationConstraintView>(R.id.bottom_navigation)
        if(navBar != null){
            navBar.isVisible = false
        }
        return mainBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //-------------------------------
        if(order!=null && user !=null) {
            if (user!!.typeUser == "Pharmacy") {
                Picasso.with(context).load(order!!.user!!["photoUser"]).transform(
                    CircleTransform()
                )
                    .into(mainBinding.imageOrder)

                mainBinding.nameDetailPharmacy.text = order!!.user!!["nameUser"]
                mainBinding.locationDetailPharmacy.text = order!!.user!!["locationUser"]

                if (order!!.state == listState[0]) {
                    mainBinding.buttonRejectOrder.isVisible = true
                    mainBinding.buttonAcceptOrder.isVisible = true
                } else {
                    mainBinding.buttonRejectOrder.isVisible = false
                    mainBinding.buttonAcceptOrder.isVisible = false
                }
            } else {
                if (order!!.state == listState[0]) {
                    mainBinding.buttonRejectOrder.isVisible = false
                    mainBinding.buttonAcceptOrder.text = "Annuler"
                    mainBinding.buttonAcceptOrder.isVisible = true
                } else {
                    mainBinding.buttonRejectOrder.isVisible = false
                    mainBinding.buttonAcceptOrder.isVisible = false
                }


                Log.v("PHOTOTAG","photo ${order!!.pharmacy!!["photoPharmacy"]}")
                Picasso.with(context).load(order!!.pharmacy!!["photoPharmacy"]).transform(CircleTransform()).into(mainBinding.imageOrder)

                mainBinding.nameDetailPharmacy.text = order!!.pharmacy!!["namePharmacy"]
                mainBinding.locationDetailPharmacy.text = order!!.pharmacy!!["locationPharmacy"]
            }

            returnPhotos(order!!.photoOrders!!)


            listFile.observe(
                viewLifecycleOwner
            ) {
                mainBinding.GridRecycleView.adapter = GridImageAdapter(activity, it)
            }
            mainBinding.inputNote.setText(order!!.note)
            mainBinding.inputNote.isEnabled = false

        }

        mainBinding.buttonPhoneOrder.setOnClickListener{
            viewModel.phoneNumber(order,type,requireActivity())
        }
        mainBinding.buttonMapLocationOrder.setOnClickListener{
            viewModel.locationUser(order, requireActivity())
        }
        mainBinding.buttonAcceptOrder.setOnClickListener {
            viewModel.acceptOrder(order,type,requireActivity())
        }
        mainBinding.buttonRejectOrder.setOnClickListener {
            viewModel.rejectOrder(order,type,requireActivity())
        }
        //-------------------------------

    }

    override fun onDestroy() {
        super.onDestroy()
        destroyAllFiles()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        destroyAllFiles()
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

