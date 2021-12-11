package com.example.mymoviecatalog.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.mymoviecatalog.Network.ApiService
import com.example.mymoviecatalog.R
import com.example.mymoviecatalog.base.BaseFragment
import com.example.mymoviecatalog.data.FilmDetails
import com.example.mymoviecatalog.data.Trailer
import com.example.mymoviecatalog.extensions.viewModel
import com.example.mymoviecatalog.viewModel.FilmViewModel
import com.squareup.picasso.Picasso
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.film_details_fragment.*
import kotlinx.android.synthetic.main.trailers.*
import kotlinx.android.synthetic.main.video.view.*

class FilmDetailsFragment : BaseFragment(), View.OnClickListener {
    private lateinit var filmViewModel: FilmViewModel
    private lateinit var filmDetailsModel: FilmDetails
    private lateinit var trailerId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.film_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = arguments?.getInt("id")
        filmViewModel = viewModel(factory)
        id?.let {
            observeViewModel(it)
        }
    }

    private fun observeViewModel(id: Int) {
        filmViewModel.getFilmDetails(id)
        filmViewModel.filmLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is FilmViewModel.ViewModelViewState.SuccessFilmDetails -> {
                    filmDetailsModel =
                        FilmDetails(
                            it.data.backdropPath,
                            it.data.runtime,
                            it.data.rating,
                            it.data.title,
                            it.data.posterPath,
                            it.data.date,
                            it.data.overview
                        )
                    initUI(filmDetailsModel)
                }
            }
        })

        filmViewModel.getTrailers(id)
        filmViewModel.filmLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is FilmViewModel.ViewModelViewState.SuccessTrailers -> {
                    val trailers = it.data.results
                    showTrailers(trailers)
                }
            }
        })
    }

    private fun initUI(model: FilmDetails) {
        val backdropUrl = ApiService.BASE_URL_IMAGE_TMDB.plus(model.backdropPath)
        val posterUrl = ApiService.BASE_URL_IMAGE_TMDB.plus(model.posterPath)
        Glide.with(this).load(backdropUrl).transform(BlurTransformation()).into(backdrop)
        Picasso.get().load(posterUrl).into(poster)
        runtime_min.text = getString(R.string.runtime_min, model.runtime.toString())
        rating_text.text = model.rating.toString()
        film_details_title.text = model.title
        film_details_year.text = model.date.toString().substring(0, 4)
        overview_text.text = model.overview
    }

    private fun showTrailers(trailers: ArrayList<Trailer>) {
        trailers.forEach {
            val trailerContainer = layoutInflater.inflate(R.layout.video, trailers_layout, false)
            trailerId = it.key
            trailerContainer.setTag(R.id.trailerId, trailerId)
            val imageUrl = getString(R.string.base_youtube_thumbnails_url, trailerId)
            Picasso.get().load(imageUrl).into(trailerContainer.video_thumb)
            trailers_layout.addView(trailerContainer)
            trailerContainer.setOnClickListener(this)
        }
    }

    override fun onClick(view: View?) {
        val intent = Intent(context, WatchFilmActivity::class.java).apply{
            putExtra("id", view?.getTag(R.id.trailerId).toString())
        }
        startActivity(intent)
    }
}