package com.example.smartpharm

import java.io.Serializable

data class Pharmacy(val name:String, val adresse:String,
                    val telephone:String, val pharmaimage:Int,
                    val cord1:Double, val cord2:Double,
                    val hor_ouv:String, val hor_ferm:String,
                    val facebook_link:String, val desription:String): Serializable
