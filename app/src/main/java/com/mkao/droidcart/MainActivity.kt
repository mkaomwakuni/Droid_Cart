package com.mkao.droidcart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var navController :NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Retrieve NvController from the NavigationFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        as NavHostFragment

        navController = navHostFragment.navController
        //setup Action bar with the NavController
        setupActionBarWithNavController(this,navController)
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}