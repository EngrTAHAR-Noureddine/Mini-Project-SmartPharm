package com.example.smartpharm.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smartpharm.R
import com.example.smartpharm.activities.ClientActivity
import com.example.smartpharm.activities.PaymentActivity
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
        val navBarPh = activity?.findViewById<BubbleNavigationConstraintView>(R.id.bottom_navigation_ph)
        if(navBarPh != null){
            navBarPh.isVisible = false
        }
        return mainBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //-------------------------------
        if(order!=null && user !=null) {
            if (user!!.typeUser == "Pharmacy") { // type == pharmacy

                Picasso.with(context).load(order!!.user!!["photoUser"]).transform(CircleTransform()).into(mainBinding.imageOrder)
                mainBinding.nameDetailPharmacy.text = order!!.user!!["nameUser"]
                mainBinding.locationDetailPharmacy.text = order!!.user!!["locationUser"]

                if (order!!.state == listState[0]) { // state en cours
                    stateRejectedBtn() // true
                    stateAcceptedBtn() //true
                }else if(order!!.state == listState[2] && order!!.payment == 0){ // accept order and not paid
                    stateRejectedBtn(isVisible = false)
                    stateAcceptedBtn(typeText =  "Confirmer")
                    toggleInputPaymentField(isVisible = true, isEnabled = true)
                }
                else { // reject order or accepted and paid order
                    stateRejectedBtn(isVisible = false)
                    stateAcceptedBtn(isVisible = false)
                    toggleInputPaymentField()
                }


            } else { // user
                Log.v("order", "is paid  : ${order!!.paidOrder}")
                if (order!!.state == listState[0]) { // state en cours
                    stateRejectedBtn(typeText =  "Annuler")
                    stateAcceptedBtn(isVisible = false)
                }else if(order!!.state == listState[2] && order!!.payment != 0 && order!!.paidOrder == "NON"){ // accepted and go to pay order ( not paid yet)
                    stateAcceptedBtn(typeText =  "Payer")
                    stateRejectedBtn(isVisible = false)
                    toggleInputPaymentField(isVisible = true)
                }
                else { // payed order
                    stateRejectedBtn(isVisible = false)
                    stateAcceptedBtn(isVisible = false)
                    toggleInputPaymentField(isVisible = true)
                }

                Picasso.with(context).load(order!!.pharmacy!!["photoPharmacy"]).transform(CircleTransform()).into(mainBinding.imageOrder)
                mainBinding.nameDetailPharmacy.text = order!!.pharmacy!!["namePharmacy"]
                mainBinding.locationDetailPharmacy.text = order!!.pharmacy!!["locationPharmacy"]

            }

            returnPhotos(order!!.photoOrders!!)


            listFile.observe(viewLifecycleOwner) {
                Log.v("List","list  : $it")
                mainBinding.GridRecycleView.adapter = GridImageAdapter(activity, it)
            }
            mainBinding.inputNote.setText(order!!.note)
            mainBinding.inputNote.isEnabled = false
            mainBinding.inputPayment.setText(order!!.payment.toString())

        }

        mainBinding.buttonPhoneOrder.setOnClickListener{
            viewModel.phoneNumber(order,type,requireActivity())
        }

        mainBinding.buttonMapLocationOrder.setOnClickListener{
            viewModel.locationUser(order, requireActivity())
        }

        mainBinding.buttonAcceptOrder.setOnClickListener {

            if (user!!.typeUser == "Pharmacy") { //pharmacy

                val paid :Int? = mainBinding.inputPayment.text.toString().toInt()
                order!!.payment = paid ?: 0

                if (order!!.state == listState[0]) { // state en cours
                    stateRejectedBtn(isVisible = false) // false
                    stateAcceptedBtn(typeText =  "Confirmer") //change state
                    toggleInputPaymentField(isVisible = true, isEnabled = true)
                    viewModel.acceptOrder(order,type,requireActivity())
                }else if(order!!.state == listState[2]){
                    if( order!!.payment == 0){ // accept order and not paid
                        stateRejectedBtn(isVisible = false)
                        stateAcceptedBtn(typeText =  "Confirmer")
                        Toast.makeText(context, "Mettez un payment SVP",Toast.LENGTH_SHORT).show()
                    }
                    else { // reject order or accepted and paid order
                        viewModel.acceptOrder(order,type,requireActivity())
                        stateRejectedBtn(isVisible = false)
                        stateAcceptedBtn(isVisible = false)
                        toggleInputPaymentField()
                    }
                }

            }else{ // User

               if(order!!.state == listState[2] && order!!.payment != 0){ // accepted and go to pay order ( not paid yet)
                   //viewModel.acceptOrder(order,type,requireActivity())

                   val intent = Intent(requireContext(), PaymentActivity::class.java)
                   intent.putExtra("idOrder", order!!.idOrder)
                   intent.putExtra("paymentOrder", order!!.payment)
                   startActivity(intent)

                }

            }


        }

        mainBinding.buttonRejectOrder.setOnClickListener {
        viewModel.rejectOrder(order,type,requireActivity())
        }
        //-------------------------------

    }

    private fun toggleInputPaymentField(isVisible : Boolean = false , isEnabled : Boolean = false){
        mainBinding.InputPaymentOrderBox.isVisible = isVisible
        mainBinding.inputPayment.isEnabled = isEnabled
    }
    private fun stateAcceptedBtn(isVisible: Boolean = true, typeText:String = "Accepter"){
        mainBinding.buttonAcceptOrder.isVisible = isVisible
        mainBinding.buttonAcceptOrder.text = typeText
    }
    private fun stateRejectedBtn(isVisible: Boolean = true, typeText: String = "Rejeter"){
        mainBinding.buttonRejectOrder.isVisible = isVisible
        mainBinding.buttonRejectOrder.text = typeText
    }

    override fun onResume() {
        super.onResume()
        val gson = Gson()
        val json1: String = getData("Order", "orderDetail") ?: ""
        order = gson.fromJson(json1, Order::class.java)

        if(order!=null){
            if(order!!.paidOrder == "OUI"){
                stateRejectedBtn(isVisible = false)
                stateAcceptedBtn(isVisible = false)
                toggleInputPaymentField(isVisible = true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyAllFiles()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.v("DEST","destroy it")
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

