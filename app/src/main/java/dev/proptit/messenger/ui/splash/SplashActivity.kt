package dev.proptit.messenger.ui.splash


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import androidx.activity.ComponentActivity
import dev.proptit.messenger.MainActivity
import dev.proptit.messenger.R


class SplashActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Handler to delay the transition to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}

