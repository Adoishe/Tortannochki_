package com.adoishe.tortannochki

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView


class MainActivity (private var databaseHelper: DatabaseHelper? = null): AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var profile: Profile
    lateinit var order: Order

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        profile = Profile()

        profile.readProfile(this)

        order = Order(profile)

        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout              = findViewById(R.id.drawer_layout)
        val navView: NavigationView                 = findViewById(R.id.nav_view)
        val navController                           = findNavController(R.id.nav_host_fragment)
        val fab: ExtendedFloatingActionButton       = findViewById(R.id.fab)
        val fabSkip: ExtendedFloatingActionButton   = findViewById(R.id.fabSkip)

        fabSkip.setOnClickListener { view ->

            when ((navController.currentDestination as FragmentNavigator.Destination).className) {
                "com.adoishe.tortannochki.TasteFragment" -> navController.navigate(R.id.decorFragment)
                "com.adoishe.tortannochki.DecorFragment" -> navController.navigate(R.id.otherSweetsFragment)
                "com.adoishe.tortannochki.OtherSweetsFragment" -> navController.navigate(R.id.mapsFragment)

                else -> { // Note the block
                    print("x is neither 1 nor 2")
                }
            }

            /*
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
             */
        }

        fab.setOnClickListener { view ->

            navController.navigate(R.id.orderFragment)
                /*
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

                 */
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        databaseHelper  = DatabaseHelper(this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}
