package dev.proptit.messenger.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.proptit.messenger.R
import dev.proptit.messenger.data.contact.ContactRepository
import dev.proptit.messenger.data.message.MessageRepository
import dev.proptit.messenger.databinding.ActivityMainBinding
import dev.proptit.messenger.setup.Keys

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var navigationView: BottomNavigationView
    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by viewModels(
        factoryProducer = {
            MainViewModelFactory(
                ContactRepository(),
                MessageRepository()
            )
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        // get id account from intent
        val intent = intent
        val idAccount = intent.getIntExtra(Keys.ID_USER, -1)

        // shared preferences
        val sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(Keys.ID_MY_ACCOUNT, idAccount)
        editor.apply()


        mainViewModel.idAccount = idAccount
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

}