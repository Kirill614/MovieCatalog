package com.example.mymoviecatalog.ui

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviecatalog.R
import com.example.mymoviecatalog.Utils.ItemClickListener
import com.example.mymoviecatalog.base.BaseFragment
import com.example.mymoviecatalog.data.Movie
import com.example.mymoviecatalog.data.Person
import com.example.mymoviecatalog.data.PopMovie

import com.example.mymoviecatalog.extensions.viewModel
import com.example.mymoviecatalog.rvadapters.ActorRVAdapter
import com.example.mymoviecatalog.rvadapters.MovieRVAdapter
import com.example.mymoviecatalog.viewModel.ActorsViewModel
import com.example.mymoviecatalog.viewModel.FilmViewModel
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fargment_home.*

class HomeFragment : BaseFragment(), ItemClickListener {
    private lateinit var actorsList: ArrayList<Person>
    lateinit var filmViewModel: FilmViewModel
    lateinit var actorsViewModel: ActorsViewModel
    private var moviesMap: MutableMap<Int, java.util.ArrayList<Movie>> = mutableMapOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(activity).inflate(R.layout.fargment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as MainActivity).title = "Главная"
        filmViewModel = viewModel(factory)
        actorsViewModel = viewModel(factory)
        setupObserversForViewModel()
    }

    private fun setupRV(list: List<Person>) {
        val adapter = ActorRVAdapter(list, this)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        actors_recycler.layoutManager = layoutManager
        actors_recycler.adapter = adapter
    }

    private fun setupObserversForViewModel() {
        actorsViewModel.getPersons()
        actorsViewModel.actorsLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ActorsViewModel.ViewModelViewState.SuccessActors -> {
                    actorsList = it.data.personsList
                    initMoviesMap(actorsList)
                    setupRV(actorsList)
                }
            }
        })
        filmViewModel.getPopMovies()
        filmViewModel.filmLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is FilmViewModel.ViewModelViewState.SuccessPopMovies -> {
                    setUpPopMovieRV(it.data.popMovieList)
                }
            }
        })
    }

    private fun initMoviesMap(list: ArrayList<Person>) {
        list.forEach {
            moviesMap[it.id] = it.moviesList
        }
    }

    private fun setUpPopMovieRV(list: List<PopMovie>) {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val adapter = MovieRVAdapter(list, object : ItemClickListener {
            override fun onClick(id: Int) {
                findNavController().navigate(R.id.nav_film_details, bundleOf(Pair("id", id)))
            }
        })
        movie_RV.layoutManager = layoutManager
        movie_RV.adapter = adapter
    }

    override fun onClick(id: Int) {
        findNavController().navigate(
            R.id.homeFragment_to_actorDetails_graph,
            bundleOf(
                Pair("id", id),
                Pair("movies", moviesMap[id])
            )
        )
    }
}