package com.example.smartpharm.activities

import android.app.Application
import com.stripe.android.PaymentConfiguration

class FirebaseMobilePaymentsApp: Application(){
    override fun onCreate() {
        super.onCreate()
        PaymentConfiguration.init(applicationContext, "pk_test_51KWT4bAhAgOnf1TRY3C7aONlVy0C4rWl1roGSH7GHRz1pnWbThbJGYaojGmaA0c1s3DZpI8gitKIEgXQUH6NOc7X00eYKjV6I2")
    }
}