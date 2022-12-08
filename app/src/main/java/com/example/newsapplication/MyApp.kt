package com.example.newsapplication

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.newsapplication.utils.Constants
import com.example.newsapplication.utils.MyPreference

class MyApp: Application() {
    lateinit var  preference: MyPreference

    override fun onCreate() {
        super.onCreate()
        preference  = MyPreference.getInstance(this)!!
        val result = preference.getData(Constants.THEMETYPE)
        if (result.equals("light")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        }
    }
}