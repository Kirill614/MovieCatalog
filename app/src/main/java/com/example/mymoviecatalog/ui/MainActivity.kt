package com.example.mymoviecatalog.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mymoviecatalog.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val genresArr =
        arrayOf("Комедии", "Мелодрамы", "Боевики", "Детектив", "Фантастика", "Исторические фильмы")
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.nav_home,
                    R.id.nav_comedy,
                    R.id.nav_melodr,
                    R.id.nav_action,
                    R.id.nav_detective,
                    R.id.nav_fiction,
                    R.id.nav_history
                ), drawerLayout
            )
        navController = findNavController(R.id.nav_host_fragment_content_main)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView
        searchItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                supportFragmentManager.popBackStack()
                return true
            }
        })

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                createSearchFragment(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                navController.navigate(R.id.nav_graph_home)
                drawer_layout.closeDrawers()
               // navController.setGraph(R.navigation.mobile_navigation)
            }
            R.id.nav_comedy -> navigateToYoutubeFragment(
                R.id.nav_comedy,
                getString(R.string.comedy_playlist)
            )

            R.id.nav_melodr -> navigateToYoutubeFragment(
                R.id.nav_melodr,
                getString(R.string.melodrama_playlist)
            )
            R.id.nav_action -> navigateToYoutubeFragment(
                R.id.nav_action,
                getString(R.string.action_playlist)
            )
            R.id.nav_detective -> navigateToYoutubeFragment(
                R.id.nav_detective,
                getString(R.string.detective_playlist)
            )
            R.id.nav_fiction -> navigateToYoutubeFragment(
                R.id.nav_fiction,
                getString(R.string.fiction_playlist)
            )
            R.id.nav_history -> navigateToYoutubeFragment(
                R.id.nav_history,
                getString(R.string.history_playlist)
            )
        }
        return true
    }

    private fun navigateToYoutubeFragment(destinationId: Int, playlistId: String) {
        navController.navigate(
            destinationId, bundleOf(
                Pair("id", playlistId)
            )
        )
        drawerLayout.closeDrawers()
    }

    private fun createSearchFragment(query: String) {
        val bundle = Bundle()
        val fragment = FilmSearchFragment()
        bundle.putString("query", query)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().add(R.id.drawer_layout, fragment, "youtube")
            .addToBackStack("").commit()
    }
}