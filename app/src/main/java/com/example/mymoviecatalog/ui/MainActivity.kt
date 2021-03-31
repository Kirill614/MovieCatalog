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
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.new_main_activity.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var playlistIdMap: MutableMap<String, String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_main_activity)
        setSupportActionBar(toolbar)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        initNavigationView()

        addFragment(InfoFragment())

        initMap()
    }

    private fun initMap() {
        playlistIdMap = mutableMapOf(
            "comedy" to "PLKFv49NwZ7po_QKkvibu5sdtM94q_tJsV",
            "melodrama" to "PLKFv49NwZ7prfhWGzOUPhGLJEkKJuATSF",
            "action" to "PLKFv49NwZ7ppW9hutJtfz5v8C8q7M0jj_",
            "detective" to "PLKFv49NwZ7ppfrpa59mYdyXm_7buLbw2p",
            "fiction" to "PLKFv49NwZ7prnq3GHXGUjY_fgQtJbcP4Y",
            "history" to "PLKFv49NwZ7pqL-XXApPKqRubl3MwVOK54"
        )
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
            R.id.nav_comedy -> playlistIdMap["comedy"]?.let { createYoutubeFilmFragment(it,"Комедии") }
            R.id.nav_melodr -> playlistIdMap["melodrama"]?.let { createYoutubeFilmFragment(it,"Мелодрама") }
            R.id.nav_action -> playlistIdMap["action"]?.let{createYoutubeFilmFragment(it,"Боевики")}
            R.id.nav_detective -> playlistIdMap["detective"]?.let{createYoutubeFilmFragment(it,"Детективы")}
            R.id.nav_fiction -> playlistIdMap["fiction"]?.let{createYoutubeFilmFragment(it,"Фантастика")}
            R.id.nav_history -> playlistIdMap["history"]?.let{createYoutubeFilmFragment(it,"Исторические фильмы")}
        }
        return true
    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.layout_app_bar, fragment, "")
            .commit()
        drawer_layout.closeDrawers()
    }

     private fun createSearchFragment(query: String){
        val bundle = Bundle()
        val fragment = FilmSearchFragment()
        bundle.putString("query",query)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().add(R.id.drawer_layout,fragment,"")
            .addToBackStack("").commit()
    }

    private fun createYoutubeFilmFragment(playlistId: String, title: String) {
        val bundle = Bundle()
        bundle.putString("id", playlistId)
        bundle.putString("title",title)
        val fragment = YoutubeFilmFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().add(R.id.layout_app_bar, fragment, "fr")
            .addToBackStack("").commit()
        drawer_layout.closeDrawers()
    }
}