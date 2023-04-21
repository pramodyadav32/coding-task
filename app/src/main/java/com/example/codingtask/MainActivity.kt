package com.example.codingtask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController


/*
There are few changes I made which I feels the correct way to do.
1. The logic which was implemented in MainActivity to drwa the pose and other are moved to FirstFragment and FirstFragment is implemented as initial fragment
2. Some hard coded strings from xml are moved to string resources
3.

 */

class MainActivity : AppCompatActivity() {

    private var navController: NavController? = null
    private var navigationBar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigation_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navigationBar = findViewById(R.id.navigationBar)
        val appBarConfiguration = AppBarConfiguration(navController!!.graph)
        findViewById<Toolbar>(R.id.navigationBar).setupWithNavController(navController!!, appBarConfiguration)

        setSupportActionBar(navigationBar)
        setupActionBarWithNavController(findNavController(R.id.navigation_host_fragment))
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navigation_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}
