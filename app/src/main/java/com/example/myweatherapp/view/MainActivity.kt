package com.example.myweatherapp.view

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.myweatherapp.R
import com.example.myweatherapp.databinding.ActivityMainBinding
import com.example.myweatherapp.view.main.MainFragment
import com.example.myweatherapp.view.MainBroadcastReceiver
import com.example.myweatherapp.view.history.HistoryFragment
import com.example.myweatherapp.view.provider.ContentProviderFragment

class MainActivity : AppCompatActivity() {

    private val receiver = MainBroadcastReceiver()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitAllowingStateLoss()
        }
        registerReceiver(receiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
    }
    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                supportFragmentManager.apply {
                    beginTransaction()
                            .add(R.id.container, HistoryFragment.newInstance())
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                }
                true
            }
            R.id.menu_google_maps -> {
                supportFragmentManager.apply {
                    beginTransaction()
                            .add(R.id.container, ContentProviderFragment.newInstance())
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}