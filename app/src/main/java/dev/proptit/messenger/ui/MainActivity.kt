package dev.proptit.messenger.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.proptit.messenger.MyApp
import dev.proptit.messenger.R
import dev.proptit.messenger.data.chat.Contact
import dev.proptit.messenger.data.message.Message
import dev.proptit.messenger.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var navigationView: BottomNavigationView
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        navigationView = mainBinding.bottomNav
        setContentView(mainBinding.root)
        initFakeData()
        navController = findNavController(R.id.navHostFragment)
        navigationView.setupWithNavController(navController)
    }

    private fun initFakeData() {
        lifecycleScope.launch {
            MyApp.getInstance().database.apply {
                contactDao().apply {
                    addContact(
                        Contact(id = 1, "Martin Randolph", R.drawable.image_ps1, false)
                    )
                    addContact(
                        Contact(id = 2, "Andrew Parker", R.drawable.image_ps2, true)
                    )
                    addContact(
                        Contact(id = 3, "Karen Castillo", R.drawable.image_ps3, false)
                    )
                    addContact(
                        Contact(id = 4, "Maisy Humphrey", R.drawable.image_ps4, true)
                    )
                }

                messageDao().apply {
                    addMessage(
                        Message(id = 1, 1, 0, "Hello")
                    )
                    addMessage(
                        Message(id = 2, 2, 0, "Hi")
                    )
                    addMessage(
                        Message(id = 3, 3, 0, "Hey")
                    )
                    addMessage(
                        Message(id = 4, 4, 0, "What's up bro")
                    )
                }
            }
        }
    }
}