package com.ing.android.coolusers.view.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

import com.ing.android.coolusers.R
import com.ing.android.coolusers.view.EVENT_USER_LIST_LOAD_FAILED
import com.ing.android.coolusers.view.EVENT_USER_LOAD_FAILED

class MainActivity : AppCompatActivity(), NavHost {

    private val serviceFailureReceiver: ServiceFailureReceiver by lazy {
        ServiceFailureReceiver()
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(
                serviceFailureReceiver, IntentFilter(EVENT_USER_LIST_LOAD_FAILED))
        LocalBroadcastManager.getInstance(this).registerReceiver(
                serviceFailureReceiver, IntentFilter(EVENT_USER_LOAD_FAILED))
    }

    override fun onSupportNavigateUp() =
            navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    override fun getNavController() =
            findNavController(R.id.nav_host_fragment)

    private inner class ServiceFailureReceiver : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            when (intent?.action) {
                EVENT_USER_LIST_LOAD_FAILED ->
                    Toast.makeText(this@MainActivity,
                            resources.getString(R.string.user_list_loading_failed), Toast.LENGTH_SHORT).show()
                EVENT_USER_LOAD_FAILED ->
                    Toast.makeText(this@MainActivity,
                            resources.getString(R.string.user_loading_failed), Toast.LENGTH_SHORT).show()
            }
        }

    }
}