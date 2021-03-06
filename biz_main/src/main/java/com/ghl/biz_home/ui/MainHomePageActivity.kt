package com.ghl.biz_home.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ghl.biz_home.R
import com.ghl.common.constants.main.ACTIVITY_HOME
import com.ghl.common.util.StatusBarUtils
import com.ghl.router_annotation.Route
import com.google.android.material.bottomnavigation.BottomNavigationView

@Route(ACTIVITY_HOME)
class MainHomePageActivity : AppCompatActivity() {

    private val mNavigator: BottomNavigationView by lazy {
        findViewById<BottomNavigationView>(R.id.nav_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home_page)

        StatusBarUtils.transparencyBar(this) //设置状态栏全透明
        StatusBarUtils.StatusBarLightMode(this) //设置白底黑字

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //val appBarConfiguration = AppBarConfiguration(setOf(
        //R.id.navigation_home, R.id.navigation_flutter, R.id.navigation_notifications, R.id.navigation_mine))
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        mNavigator.run{
//            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
//            selectedItemId = R.id.navigation_home
//        }




    }


//    /**
//     * NavigationItemSelect监听
//     */
//    private val onNavigationItemSelectedListener =
//            BottomNavigationView.OnNavigationItemSelectedListener { item ->
//                setFragment(item.itemId)
//                return@OnNavigationItemSelectedListener when (item.itemId) {
//                    R.id.navigation_home -> {
//                        if (currentIndex == R.id.navigation_home) {
//                            homeFragment?.smoothScrollToPosition()
//                        }
//                        currentIndex = R.id.navigation_home
//                        true
//                    }
//                    R.id.navigation_type -> {
//                        if (currentIndex == R.id.navigation_type) {
//                            typeFragment?.smoothScrollToPosition()
//                        }
//                        currentIndex = R.id.navigation_type
//                        true
//                    }
//                    else -> {
//                        false
//                    }
//                }
//            }
}
