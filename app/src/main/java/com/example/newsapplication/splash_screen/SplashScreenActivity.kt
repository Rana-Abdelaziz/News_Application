package com.example.newsapplication.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.example.newsapplication.R
import androidx.lifecycle.lifecycleScope
import com.example.newsapplication.main_activity.view.MainActivity
import com.example.newsapplication.utils.Constants
import com.example.newsapplication.utils.MyPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreenActivity : AppCompatActivity() {

    private val splashScreenScope = lifecycleScope
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        splashScreenScope.launch(Dispatchers.Default) {
            delay(5000)
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}