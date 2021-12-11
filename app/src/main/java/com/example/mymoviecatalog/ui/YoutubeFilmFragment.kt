package com.example.mymoviecatalog.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviecatalog.R
import com.example.mymoviecatalog.Utils.ItemClickListener
import com.example.mymoviecatalog.Utils.OnFilmClickListener
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
import java.lang.StringBuilder
import javax.inject.Inject
import javax.inject.Named

class YoutubeFilmFragment() : BaseFragment(), OnFilmClickListener {
    lateinit var viewModel: YouTubeViewModel
    lateinit var filmsList: ArrayList<Film>
    var playlistId: String? = null
    private lateinit var mainActivity: MainActivity
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//        playlistId = arguments?.getString("id").toString()
//        playlistId?.let { setTitle(it) }
//        val title = arguments?.getString("title")
//        activity?.title = title
//
//        viewModel = viewModel(factory)
//        setupObserversForViewModel()
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(activity)
            .inflate(R.layout.fragment_youtube_films, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        mainActivity = activity as MainActivity
        playlistId = arguments?.getString("id").toString()
        playlistId?.let { setTitle(it) }

        viewModel = viewModel(factory)
        setupObserversForViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }

    private fun setTitle(playListId: String){
        when(playListId){
            getString(R.string.comedy_playlist) -> mainActivity.title = "Комедии"
            getString(R.string.melodrama_playlist) -> mainActivity.title = "Мелодрамы"
            getString(R.string.action_playlist) -> mainActivity.title = "Боевики"
            getString(R.string.detective_playlist) -> mainActivity.title = "Детектив"
            getString(R.string.fiction_playlist) -> mainActivity.title = "Фантастика"
            getString(R.string.history_playlist) -> mainActivity.title = "Исторические фильмы"
        }
    }

    private fun setupRV() {
        val adapter = YoutubeRvAdapter(filmsList, this)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        youtube_rv.adapter = adapter
        youtube_rv.layoutManager = layoutManager
    }

    private fun setupObserversForViewModel() {
        // viewModel.getYoutubeFilms(playlistId)
//        playlistId?.let {
//            viewModel.getYoutubeFilms(it)
//        }
        if (playlistId != null){
            viewModel.getYoutubeFilms(playlistId!!)
        }
        viewModel.youtubeLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is YouTubeViewModel.ViewModelViewState.SuccessYoutubeFilms -> {
                    filmsList = it.data.items
                    setupRV()
                }
            }
        })
    }

    override fun onClick(id: String, description: String) {
        createActivity(WatchFilmActivity::class.java, id, description)
    }
}