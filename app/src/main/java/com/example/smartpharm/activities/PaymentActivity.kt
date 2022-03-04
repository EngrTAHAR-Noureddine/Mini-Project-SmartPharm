package com.example.smartpharm.activities

import android.annotation.SuppressLint
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartpharm.controllers.FirebaseEphemeralKeyProvider
import com.example.smartpharm.controllers.OrderController.updateOrderPaid
import com.example.smartpharm.database.DataBase.db
import com.example.smartpharm.databinding.ActivityPaymentBinding
import com.example.smartpharm.models.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import com.stripe.android.*
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethod
import com.stripe.android.view.BillingAddressFields
import kotlinx.android.synthetic.main.activity_payment.*


var RC_SIGN_IN = 1
class PaymentActivity : AppCompatActivity() {
    private var currentUser: FirebaseUser? = null
    private lateinit var paymentSession: PaymentSession
    private lateinit var selectedPaymentMethod: PaymentMethod
    private val stripe: Stripe by lazy { Stripe(applicationContext, PaymentConfiguration.getInstance(applicationContext).publishableKey) }
    private var paymentOrder : Int = 0
    private lateinit var binding : ActivityPaymentBinding
    private var order: Order? = null

    private fun getData(file:String, string: String): String?{
        val prefUser = getSharedPreferences(file, Context.MODE_PRIVATE)
        return prefUser?.getString(string,"")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentBinding.inflate(layoutInflater)

        val gson = Gson()
        val json1: String = getData("Order", "orderDetail") ?: ""
        order = gson.fromJson(json1, Order::class.java)

        setContentView(binding.root)
        paymentOrder = intent.getIntExtra("paymentOrder",0)

        payButton.setOnClickListener {
            confirmPayment(selectedPaymentMethod.id!!)
        }




        loginButton.setOnClickListener {
            // login to firebase
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build())

            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN
            )
        }

        payButton.setOnClickListener {
            confirmPayment(selectedPaymentMethod.id!!)
        }

        paymentmethod.setOnClickListener {
            // Create the customer session and kick start the payment flow
            paymentSession.presentPaymentMethodSelection()
        }

        showUI()
    }

    @SuppressLint("SetTextI18n")
    private fun confirmPayment(paymentMethodId: String) {
        payButton.isEnabled = false

        val paymentCollection = db
            .collection("stripe_customers").document(currentUser?.uid?:"")
            .collection("payments")

        // Add a new document with a generated ID
        paymentCollection.add(hashMapOf(
            "amount" to paymentOrder,
            "currency" to "usd"
        ))
            .addOnSuccessListener { documentReference ->
                Log.d("payment", "DocumentSnapshot added with ID: ${documentReference.id}")
                documentReference.addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w("payment", "Listen failed.", e)
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Log.d("payment", "Current data: ${snapshot.data}")
                        val clientSecret = snapshot.data?.get("client_secret")
                        Log.d("payment", "Create paymentIntent returns $clientSecret")
                        clientSecret?.let {

                            stripe.confirmPayment(this, ConfirmPaymentIntentParams.createWithPaymentMethodId(
                                paymentMethodId,
                                (it as String)
                            ))

                            binding.checkoutSummary.text = "Merci pour le payment"
                            Toast.makeText(applicationContext, "Payment Done!!", Toast.LENGTH_LONG).show()

                            order?.let { it1 ->
                                it1.paidOrder = "OUI"
                                val pref = getSharedPreferences("Order", Context.MODE_PRIVATE)
                                val editor : SharedPreferences.Editor = pref.edit()
                                val gson = Gson()
                                val json = gson.toJson(it1)
                                editor.apply{
                                    putString("orderDetail",json)
                                }.apply()
                                updateOrderPaid(it1, this)
                            }

                            finish()

                        }
                    } else {
                        Log.e("payment", "Current payment intent : null")
                        binding.payButton.isEnabled = true
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.w("payment", "Error adding document", e)
                binding.payButton.isEnabled = true
            }
    }

    @SuppressLint("SetTextI18n")
    private fun showUI() {
        currentUser?.let {
            binding.loginButton.visibility = View.INVISIBLE
            binding.photoLogo.visibility = View.INVISIBLE

            //binding.payButton.isEnabled = true
            binding.miniLogo.visibility = View.VISIBLE
            binding.greeting.visibility = View.VISIBLE
            binding.checkoutSummary.visibility = View.VISIBLE
            binding.payButton.visibility = View.VISIBLE
            binding.paymentmethod.visibility = View.VISIBLE
            binding.paymentmethod.text = "Selectionnez une methode de payment"
            binding.greeting.text = "Bonjour  ${it.displayName}"
            binding.checkoutSummary.text = "Vous allez payer : $paymentOrder DZ"
            setupPaymentSession()
        }?: run {
            // User does not login
            binding.loginButton.visibility = View.VISIBLE
            binding.photoLogo.visibility = View.VISIBLE

            binding.miniLogo.visibility = View.INVISIBLE
            binding.greeting.visibility = View.INVISIBLE
            binding.checkoutSummary.visibility = View.INVISIBLE
            binding.paymentmethod.visibility = View.INVISIBLE
            binding.payButton.visibility = View.INVISIBLE
            binding.payButton.isEnabled = false

        }
    }

    private fun setupPaymentSession () {
        // Setup Customer Session
        CustomerSession.initCustomerSession(this, FirebaseEphemeralKeyProvider())
        // Setup a payment session
        paymentSession = PaymentSession(this, PaymentSessionConfig.Builder()
            .setShippingInfoRequired(false)
            .setShippingMethodsRequired(false)
            .setBillingAddressFields(BillingAddressFields.None)
            .setShouldShowGooglePay(true)
            .build())

        paymentSession.init(
            object: PaymentSession.PaymentSessionListener {
                @SuppressLint("SetTextI18n")
                override fun onPaymentSessionDataChanged(data: PaymentSessionData) {
                    Log.d("PaymentSession", "PaymentSession has changed: $data")
                    Log.d("PaymentSession", "${data.isPaymentReadyToCharge} <> ${data.paymentMethod}")

                    if (data.isPaymentReadyToCharge) {
                        Log.d("PaymentSession", "Ready to charge")
                        binding.payButton.isEnabled = true

                        data.paymentMethod?.let {
                            Log.d("PaymentSession", "PaymentMethod $it selected")
                            paymentmethod.text = "${it.card?.brand} card ends with ${it.card?.last4}"
                            selectedPaymentMethod = it
                        }
                    }
                }

                override fun onCommunicatingStateChanged(isCommunicating: Boolean) {
                    Log.d("PaymentSession",  "isCommunicating $isCommunicating")
                }

                override fun onError(errorCode: Int, errorMessage: String) {
                    Log.e("PaymentSession",  "onError: $errorCode, $errorMessage")
                }
            }
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                currentUser = FirebaseAuth.getInstance().currentUser

                Log.d("Login", "User ${currentUser?.uid} has signed in.")
                Toast.makeText(applicationContext, "Bonjour ${currentUser?.displayName}", Toast.LENGTH_SHORT).show()
                showUI()
            } else {
                Log.d("Login", "Signing in failed!")
                Toast.makeText(applicationContext, response?.error?.message?:"Sign in failed", Toast.LENGTH_LONG).show()
            }
        } else {
            paymentSession.handlePaymentData(requestCode, resultCode, data ?: Intent())
        }
    }

}