package com.example.newsapplication.utils

import android.content.Context
import android.content.SharedPreferences

class MyPreference(var context: Context) {

    var sharedPreferences: SharedPreferences

    init {
         sharedPreferences =
            context.getSharedPreferences("YourCustomNamedPreference", Context.MODE_PRIVATE)
    }
    companion object {
        private var instance: MyPreference? = null
        fun getInstance(context: Context): MyPreference? {
            return instance ?: MyPreference(context)
        }
    }


   fun saveData(key: String?, value: String?) {
        val prefsEditor = sharedPreferences.edit()
        prefsEditor.putString(key, value!!)
        prefsEditor.apply()
    }

    fun getData(key: String?): String? {
        return if (sharedPreferences != null) {
            sharedPreferences.getString(key, "")
        } else ""
    }

    fun getDataWithCustomDefaultValue(key: String?, defaultValue : String?): String? {
        return if (sharedPreferences != null) {
            sharedPreferences.getString(key, defaultValue)
        } else defaultValue
    }


}