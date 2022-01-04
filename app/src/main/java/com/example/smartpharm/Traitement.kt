package com.example.smartpharm

import java.io.Serializable
import java.util.*

data class Traitement(val name:String, val traitementimage:Int,
                      val date_debut:String,
                      val duree:String, val nbr_fois:String):Serializable
