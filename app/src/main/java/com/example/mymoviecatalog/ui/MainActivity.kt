package com.example.mymoviecatalog.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mymoviecatalog.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val genresArr =
        arrayOf("Комедии", "Мелодрамы", "Боевики", "Детектив", "Фантастика", "Исторические фильмы")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        initNavigationView()
        addFragment(InfoFragment())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAndRemoveTask()
    }

    private fun initNavigationView() {
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout, toolbar,
            R.string.openDrawer,
            R.string.closeDrawer
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
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
            R.id.nav_info -> addFragment(InfoFragment())
            R.id.nav_comedy -> createYoutubeFilmFragment(
                getString(R.string.comedy_playlist),
                genresArr[0]
            )
            R.id.nav_melodr -> createYoutubeFilmFragment(
                getString(R.string.melodrama_playlist),
                genresArr[1]
            )
            R.id.nav_action -> createYoutubeFilmFragment(
                getString(R.string.action_playlist),
                genresArr[2]
            )
            R.id.nav_detective -> createYoutubeFilmFragment(
                getString(R.string.detective_playlist),
                genresArr[3]
            )
            R.id.nav_fiction -> createYoutubeFilmFragment(
                getString(R.string.fiction_playlist),
                genresArr[4]
            )
            R.id.nav_history -> createYoutubeFilmFragment(
                getString(R.string.history_playlist),
                genresArr[5]
            )
        }
        return true
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.layout_app_bar, fragment, "info")
            .commit()
        drawer_layout.closeDrawers()
    }

    private fun createSearchFragment(query: String) {
        val bundle = Bundle()
        val fragment = FilmSearchFragment()
        bundle.putString("query", query)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().add(R.id.drawer_layout, fragment, "youtube")
            .addToBackStack("").commit()
    }

    private fun createYoutubeFilmFragment(playlistId: String, title: String) {
        val bundle = Bundle()
        bundle.putString("id", playlistId)
        bundle.putString("title", title)
        val fragment = YoutubeFilmFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().add(R.id.layout_app_bar, fragment, "fr")
            .addToBackStack("").commit()
        drawer_layout.closeDrawers()
    }
}