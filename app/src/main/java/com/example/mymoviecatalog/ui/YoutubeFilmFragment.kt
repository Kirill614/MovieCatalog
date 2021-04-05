package com.example.mymoviecatalog.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviecatalog.R
import com.example.mymoviecatalog.base.BaseFragment
import com.example.mymoviecatalog.extensions.viewModel
import com.example.mymoviecatalog.data.Film
import com.example.mymoviecatalog.di.AppComponent
import com.example.mymoviecatalog.di.DaggerAppComponent
import com.example.mymoviecatalog.di.Factory
import com.example.mymoviecatalog.rvadapters.YoutubeRvAdapter
import com.example.mymoviecatalog.viewModel.YouTubeViewModel
import kotlinx.android.synthetic.main.fragment_youtube_films.*
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class YoutubeFilmFragment(): BaseFragment() {
    lateinit var viewModel: YouTubeViewModel
    lateinit var filmsList: ArrayList<Film>
    lateinit var playlistId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playlistId = arguments?.getString("id").toString()
        val title = arguments?.getString("title")
        activity?.title = title

        val component = DaggerAppComponent.builder().application(activity).build()
        component.inject(this)

        viewModel = viewModel(factory)
        setupObserversForViewModel()
    }

    private fun setupRV() {
        val adapter = YoutubeRvAdapter(filmsList, object :
            OnFilmClickListener {
            override fun onClick(id: String, description: String?) {
                createActivity(WatchFilmActivity::class.java, id, description)
            }
        })
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        youtube_rv.adapter = adapter
        youtube_rv.layoutManager = layoutManager
    }

    private fun setupObserversForViewModel() {
        viewModel.getYoutubeFilms(playlistId.toString())
        viewModel.youtubeLiveData.observe(this, Observer {
            when (it) {
                is YouTubeViewModel.ViewModelViewState.SuccessYoutubeFilms -> {
                    filmsList = it.data.items
                    setupRV()
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(activity)
            .inflate(R.layout.fragment_youtube_films, container, false)
    }

    interface OnFilmClickListener {
        fun onClick(id: String, description: String?)
    }

}