package com.example.mymoviecatalog.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviecatalog.R
import com.example.mymoviecatalog.Utils.ItemClickListener
import com.example.mymoviecatalog.data.ActorDetailsModel
import com.example.mymoviecatalog.data.Movie
import com.example.mymoviecatalog.di.DaggerAppComponent
import com.example.mymoviecatalog.di.Factory
import com.example.mymoviecatalog.extensions.viewModel
import com.example.mymoviecatalog.rvadapters.ActorsFilmsAdapter
import com.example.mymoviecatalog.viewModel.ActorsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.actor_detail_activity.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.ArrayList
import javax.inject.Inject

class ActorDetailsActivity : AppCompatActivity() {
    private var imageUrl: String = "https://image.tmdb.org/t/p/w500"
    //private var personDetails: ActorDetailsModel? = null
    private var movieList: ArrayList<Movie>? = null
    private var id: Int? = null
    private lateinit var adapter: ActorsFilmsAdapter

    @Inject
    lateinit var factory: Factory
    private lateinit var viewModel: ActorsViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actor_detail_activity)
        setSupportActionBar(toolbar)

        val component = DaggerAppComponent.builder().application(this).build()
        component.inject(this)

        getParcelable()
        setupObserverForViewModel()
    }

    private fun setupObserverForViewModel(){
        viewModel = viewModel(factory)
        id?.let { viewModel.getActorDetails(it) }
        viewModel.actorsLiveData.observe(this, Observer {
            when(it){
                is ActorsViewModel.ViewModelViewState.SuccessActorDetails -> {
                    val actorDetails =
                        ActorDetailsModel(
                            it.data.biography,
                            it.data.name,
                            it.data.profilePath
                        )
                    setActorsDetails(actorDetails)
                }
            }
        })
    }

    private fun setupRV() {
        movieList?.let {
            adapter = ActorsFilmsAdapter(it, object : ItemClickListener {
                override fun onClick(id: Int) {
                    val intent = Intent(this@ActorDetailsActivity, FilmDetailsActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("id", id.toString())
                    intent.putExtra("bundle", bundle)
                    startActivity(intent)
                }
            })
        }
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        films_recycler.adapter = adapter
        films_recycler.layoutManager = layoutManager
    }

    private fun getParcelable() {
        val bundle = intent.getBundleExtra("bundle")
        id = bundle!!.getInt("id")
        movieList = bundle.getParcelableArrayList("movies")
    }

    private fun setActorsDetails(personDetails: ActorDetailsModel) {
        title = personDetails?.name
        val profileImageUrl = imageUrl.plus(personDetails!!.profilePath)
        Picasso.get().load(profileImageUrl).into(actor_image)
        biography_textView.text = personDetails!!.biography
        setupRV()
    }
}