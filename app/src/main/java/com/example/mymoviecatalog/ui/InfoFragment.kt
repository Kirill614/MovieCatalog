package com.example.mymoviecatalog.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviecatalog.R
import com.example.mymoviecatalog.Utils.ItemClickListener
import com.example.mymoviecatalog.base.BaseFragment
import com.example.mymoviecatalog.data.ActorDetailsModel
import com.example.mymoviecatalog.data.Movie
import com.example.mymoviecatalog.data.Person
import com.example.mymoviecatalog.data.PopMovie

import com.example.mymoviecatalog.extensions.viewModel
import com.example.mymoviecatalog.rvadapters.ActorRVAdapter
import com.example.mymoviecatalog.rvadapters.MovieRVAdapter
import com.example.mymoviecatalog.viewModel.ActorsViewModel
import com.example.mymoviecatalog.viewModel.FilmViewModel
import kotlinx.android.synthetic.main.fargment_info.*

class InfoFragment : BaseFragment() {
    private lateinit var actorsList: ArrayList<Person>
    lateinit var filmViewModel: FilmViewModel
    lateinit var actorsViewModel: ActorsViewModel
    lateinit var actorsModel: ActorDetailsModel
    private var moviesMap: MutableMap<Int, java.util.ArrayList<Movie>> = mutableMapOf()
    private lateinit var movieList: java.util.ArrayList<Movie>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        filmViewModel = viewModel(factory)
        actorsViewModel = viewModel(factory)
        setupObserversForViewModel()
        return LayoutInflater.from(activity).inflate(R.layout.fargment_info, container, false)
    }

    private fun setupRV(list: List<Person>) {
        val adapter =
            ActorRVAdapter(list, object : ItemClickListener {
                override fun onClick(id: Int) {
                   // actorsViewModel.getActorDetails(id)

                    createActivity(ActorDetailsActivity::class.java, id, createMovieList(id))
                }
            })
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        actors_recycler.layoutManager = layoutManager
        actors_recycler.adapter = adapter
    }

    private fun setupObserversForViewModel() {
        actorsViewModel.getPersons()
        actorsViewModel.actorsLiveData.observe(viewLifecycleOwner, Observer{
            when(it){
                is ActorsViewModel.ViewModelViewState.SuccessActors -> {
                    actorsList = it.data.personsList
                    setupRV(actorsList)
                }
            }
        })
        filmViewModel.getPopMovies()
        filmViewModel.filmLiveData.observe(viewLifecycleOwner, Observer{
            when(it){
                is FilmViewModel.ViewModelViewState.SuccessPopMovies ->{
                    setUpPopMovieRV(it.data.popMovieList)
                }
            }
        })
    }

    private fun createMovieList(id: Int): ArrayList<Movie>{
        actorsList.forEach{
            moviesMap[it.id] = it.moviesList
        }
        return moviesMap[id] as ArrayList<Movie>
    }

    private fun setUpPopMovieRV(list: List<PopMovie>) {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val adapter = MovieRVAdapter(list, object : ItemClickListener {
            override fun onClick(id: Int) {
                createActivity(FilmDetailsActivity::class.java, id.toString())
            }
        })
        movie_RV.layoutManager = layoutManager
        movie_RV.adapter = adapter
    }

}