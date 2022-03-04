package com.example.smartpharm.controllers

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.smartpharm.notification.MyBroadcastReceiver
import java.util.*

object MyAlarmManager {

    private val listTypes : List<String> = listOf("Lunch","Dinner")

    @RequiresApi(Build.VERSION_CODES.M)
    fun setAlarm(context : Context, hour:Int, minute:Int,id:String,type:Int){
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)

            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, 1)
            }
        }

        startAlarm(context,calendar,id,type)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    private fun startAlarm(context : Context, calendar: Calendar,id:String,type:Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MyBroadcastReceiver::class.java)
        val code : Int = id.hashCode() + listTypes[type].hashCode()
        val pendingIntent = PendingIntent.getBroadcast(context, code, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun cancelAlarm(context : Context, id:String,type:Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MyBroadcastReceiver::class.java)
        val code : Int = id.hashCode() +  listTypes[type].hashCode()
        val pendingIntent = PendingIntent.getBroadcast(context, code, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(pendingIntent)
    }
}