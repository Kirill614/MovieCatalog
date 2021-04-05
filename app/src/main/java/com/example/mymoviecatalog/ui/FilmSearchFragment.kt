package com.example.mymoviecatalog.ui

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mymoviecatalog.Network.ApiHelper
import com.example.mymoviecatalog.Network.ApiService
import com.example.mymoviecatalog.R
import com.example.mymoviecatalog.Repository.MainRepository
import com.example.mymoviecatalog.Utils.CoroutineContextProvieder
import com.example.mymoviecatalog.data.FoundMovie
import com.example.mymoviecatalog.Utils.ItemClickListener
import com.example.mymoviecatalog.base.BaseFragment
import com.example.mymoviecatalog.extensions.viewModel
import com.example.mymoviecatalog.rvadapters.SearchFilmAdapter
import com.example.mymoviecatalog.viewModel.FilmViewModel
import kotlinx.android.synthetic.main.search_fragment.*

class FilmSearchFragment : BaseFragment(), ItemClickListener {
    private lateinit var filmViewModel: FilmViewModel
    private lateinit var foundFilmsList: ArrayList<FoundMovie>
    lateinit var queryString: String

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        queryString = arguments?.getString("query").toString()
        filmViewModel = viewModel(factory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObserversForViewModel()
    }

    override fun onClick(id: Int) {
        createActivity(FilmDetailsActivity::class.java, id)
    }

    private fun setupObserversForViewModel() {
        filmViewModel.searchFilms(queryString)
        filmViewModel.filmLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is FilmViewModel.ViewModelViewState.SuccessMovieSearch -> {
                    foundFilmsList = it.data.foundMovieList
                    setupMovieSearchRV()
                }
            }
        })
    }

    private fun setupMovieSearchRV(){
        val adapter = SearchFilmAdapter(foundFilmsList, this)
        val layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        search_recycler.layoutManager = layoutManager
        search_recycler.adapter = adapter
    }

}