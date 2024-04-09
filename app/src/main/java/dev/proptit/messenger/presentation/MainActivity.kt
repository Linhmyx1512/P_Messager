package dev.proptit.messenger.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import dev.proptit.messenger.R
import dev.proptit.messenger.data.remote.NetworkManager
import dev.proptit.messenger.databinding.ActivityMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var navigationView: BottomNavigationView
    private lateinit var navController: NavController

    @Inject
    lateinit var networkManager: NetworkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkManager.register()

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        setupBottomNav()
    }

    private fun setupBottomNav() {
        navigationView = mainBinding.bottomNav
        navController = findNavController(R.id.navHostFragment)
        navigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.chatFragment -> navigationView.visibility = BottomNavigationView.GONE
                else -> navigationView.visibility = BottomNavigationView.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkManager.unregister()
    }

}