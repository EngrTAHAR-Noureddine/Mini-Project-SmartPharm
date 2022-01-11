package com.example.smartpharm.firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val gson = Gson()


object Converter {
    fun <T> T.serializeToMap(): Map<String, Any> {
        return convert()
    }
    inline fun <reified T> Map<String, Any>.toDataClass(): T {
        return convert()
    }
    inline fun <I, reified O> I.convert(): O {
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<O>() {}.type)
    }

}