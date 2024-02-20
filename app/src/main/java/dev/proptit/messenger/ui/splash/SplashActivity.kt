package dev.proptit.messenger.ui.splash


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import dev.proptit.messenger.R
import dev.proptit.messenger.databinding.ActivitySplashBinding
import dev.proptit.messenger.setup.Keys
import dev.proptit.messenger.ui.MainActivity
import dev.proptit.messenger.ui.login.LoginActivity


class SplashActivity : ComponentActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // shared preferences
        val sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val idAccount = sharedPreferences.getInt(Keys.ID_MY_ACCOUNT, -1)
        Log.d("SplashActivity", "idAccount: $idAccount")

        // Handler to delay the transition to MainActivity
        val intent = if (idAccount != -1) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, 2000)
    }
}

