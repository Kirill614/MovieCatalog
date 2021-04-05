package com.example.mymoviecatalog.base

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mymoviecatalog.di.DaggerAppComponent
import com.example.mymoviecatalog.di.Factory
import com.example.mymoviecatalog.data.Movie
import javax.inject.Inject

open class BaseFragment : Fragment() {
    @Inject
    lateinit var factory: Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component = DaggerAppComponent.builder().application(activity).build()
        component.inject(this)
    }

    fun createActivity(cls: Class<*>, id: Int) {
        val intent = Intent(activity, cls)
        val bundle = Bundle()
        bundle.putInt("id", id)
        intent.putExtra("bundle", bundle)
        startActivity(intent)
    }

    fun createActivity(cls: Class<*>, id: String, description: String?) {
        val intent = Intent(activity, cls)
        val bundle = Bundle()
        bundle.putString("id", id)
        bundle.putString("description", description)
        intent.putExtra("bundle", bundle)
        startActivity(intent)
    }

    fun createActivity(cls: Class<*>, id: Int, moviesList: ArrayList<Movie>?) {
        val intent = Intent(activity, cls)
        val bundle = Bundle()
        bundle.putInt("id", id)
        bundle.putParcelableArrayList("movies", moviesList)
        intent.putExtra("bundle", bundle)
        startActivity(intent)
    }
}