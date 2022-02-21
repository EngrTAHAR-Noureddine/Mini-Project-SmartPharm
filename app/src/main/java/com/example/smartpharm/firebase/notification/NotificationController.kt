package com.example.smartpharm.firebase.notification

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.room.FtsOptions
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.smartpharm.firebase.controllers.orders.OrderController.listState
import com.example.smartpharm.firebase.models.Order
import org.json.JSONException
import org.json.JSONObject


@SuppressLint("StaticFieldLeak")
object NotificationController {
    private var requestQueue: RequestQueue? = null
    private lateinit var ctx: Context
    private const val FCM_API = "https://fcm.googleapis.com/fcm/send"
    private const val serverKey = "key=" + "AAAAhH9ZpPw:APA91bHHOUoSJVfA0w97tUltF1WfyMY_mx10wmOuXgHdhTcKBr0F6WqIOjCMydPNVbaq_eJioWssImh1dQfDiAgoXwykhcqPZbBtkl7TJQ5y2MpceEhoj8hW29TFvp27R_Q6IIz-QsTN"
    private const val contentType = "application/json"
    const val TAG = "NOTIFICATION TAG"


    fun createNotification(order: Order, toReceiver:String,context: Context){

        val TOPIC = if(toReceiver=="Pharmacy") "/topics/${order.pharmacy!!["idPharmacy"]}" else "/topics/${order.user!!["idUser"]}"

        val NOTIFICATION_TITLE = when(order.state){
            listState[1] -> "Rejeter Ordonnance"
            listState[2] -> "Accepter Ordonnance"
            else -> "Nouveau Ordonnance"
        }
        val NOTIFICATION_MESSAGE = when(order.state){
            listState[1] -> "La pharmacie ${order.pharmacy!!["namePharmacy"]} rejete votre ordonnance"
            listState[2] -> "La pharmacie ${order.pharmacy!!["namePharmacy"]} accepte votre ordonnance"
            else -> "Votre Client ${order.user!!["nameUser"]} vous envoies une Ordonnance"
        }

        val notification = JSONObject()
        val notifcationBody = JSONObject()
        try {
            notifcationBody.put("title", NOTIFICATION_TITLE)
            notifcationBody.put("message", NOTIFICATION_MESSAGE)
            notification.put("to", TOPIC)
            notification.put("data", notifcationBody)
            Log.v("NOTIFICATION TAG", "Notification Put")
        } catch (e: JSONException) {
            Log.v("NOTIFICATION TAG", "onCreate: ${e.message}")
        }
        sendNotification(notification,context)
    }

    private fun initial(context:Context){
        ctx = context
        requestQueue = getRequestQueue()
    }

    private fun getRequestQueue(): RequestQueue? {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.applicationContext)
        }
        return requestQueue
    }
    private fun <T> addToRequestQueue(req: Request<T>) {
        getRequestQueue()!!.add(req)
    }

     private fun sendNotification(notification: JSONObject, context: Context) {
         initial(context)
        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(FCM_API, notification,
            Response.Listener<JSONObject?> { response -> Log.v(TAG, "onResponse sendNotification: $response") },
            Response.ErrorListener {
                Toast.makeText(context, "Request error", Toast.LENGTH_LONG).show()
                Log.v(TAG, "onErrorResponse of sendNotification: Didn't work")
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Authorization"] = serverKey
                params["Content-Type"] = contentType
                return params
            }
        }
         Log.v(TAG, "jsonObjectRequest : ${jsonObjectRequest.bodyContentType}")
        addToRequestQueue(jsonObjectRequest)
    }
}