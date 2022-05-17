package com.example.jpmccodingchallenge.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.jpmccodingchallenge.R
import com.example.jpmccodingchallenge.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // splash screen added here to prevent re-making the api call to get and display list
        // of schools after a app is put in the background and brought back
        // to the foreground by the user
        installSplashScreen()
        viewBinding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        supportActionBar?.title = "NYC Schools Explorer"
        viewBinding.schoolsBtn.setOnClickListener {
            startActivity(Intent(this@IntroActivity, MainActivity::class.java))
        }
    }
}