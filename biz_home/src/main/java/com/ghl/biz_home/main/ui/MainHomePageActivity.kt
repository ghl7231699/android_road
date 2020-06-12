package com.ghl.biz_home.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ghl.router_annotation.Router
import com.ghl.biz_home.R
import com.ghl.router.lib.RouterBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView

@Router("MainHomePageActivity")
class MainHomePageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home_page)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //val appBarConfiguration = AppBarConfiguration(setOf(
        //R.id.navigation_home, R.id.navigation_flutter, R.id.navigation_notifications, R.id.navigation_mine))
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
