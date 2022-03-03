package com.example.smartpharm.notification

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat.*
import com.example.smartpharm.R
import com.example.smartpharm.activities.MainActivity
import kotlin.random.Random


class MyBroadcastReceiver: BroadcastReceiver() {



    private fun getDataUser(context: Context): String?{
        val prefUser = context.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        return prefUser?.getString("userProfile","")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val notificationUtils = NotificationUtils(context)
        val notification = notificationUtils.getNotificationBuilder().build()
        val notificationID: Int = Random.nextInt(3000)
        notificationUtils.getManager().notify(notificationID, notification)

    }

}

class  NotificationUtils(base: Context) : ContextWrapper(base) {

    private val MYCHANNEL_ID = "App Alert Notification ID"
    private val MYCHANNEL_NAME = "App Alert Notification"

    private var manager: NotificationManager? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels()
        }
    }

    // Create channel for Android version 26+
    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannels() {
        val channel = NotificationChannel(MYCHANNEL_ID, MYCHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        channel.enableVibration(true)

        getManager().createNotificationChannel(channel)
    }

    // Get Manager
    fun getManager() : NotificationManager {
        if (manager == null) manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return manager as NotificationManager
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getNotificationBuilder(): Builder {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        return Builder(applicationContext, MYCHANNEL_ID)
            .setContentTitle("N'oubliez pas vos Medicaments!")
            .setContentText("Cliquez pour voir vos medicaments.")
            .setSmallIcon(R.drawable.ic_logo_24)
            .setColor(Color.YELLOW)
            .setContentIntent(pendingIntent)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setAutoCancel(true)
    }
}