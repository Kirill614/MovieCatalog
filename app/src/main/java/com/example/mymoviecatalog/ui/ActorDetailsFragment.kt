package com.example.mymoviecatalog.ui

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviecatalog.R
import com.example.mymoviecatalog.Utils.ItemClickListener
import com.example.mymoviecatalog.base.BaseFragment
import com.example.mymoviecatalog.data.ActorDetailsModel
import com.example.mymoviecatalog.data.Movie
import com.example.mymoviecatalog.extensions.viewModel
import com.example.mymoviecatalog.rvadapters.ActorsFilmsAdapter
import com.example.mymoviecatalog.viewModel.ActorsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_actor_details.*
import java.util.ArrayList

class ActorDetailsFragment : BaseFragment(), ItemClickListener {
    private var movieList: ArrayList<Movie>? = null
    private var id: Int? = null
    private lateinit var adapter: ActorsFilmsAdapter
    private lateinit var viewModel: ActorsViewModel
    private lateinit var mainActivity: MainActivity
    private var oldTitle = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_actor_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainActivity = activity as MainActivity
        id = arguments?.getInt("id")
        movieList = arguments?.getParcelableArrayList("movies")
        setupObserverForViewModel()
        setHasOptionsMenu(true)
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                mainActivity.title = oldTitle
                findNavController().popBackStack()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mainActivity.title = oldTitle
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }

    private fun setupObserverForViewModel() {
        viewModel = viewModel(factory)
        id?.let {
            viewModel.getActorDetails(it)
        }
        viewModel.actorsLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
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
        movieList?.let { adapter = ActorsFilmsAdapter(it, this) }
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        films_recycler.adapter = adapter
        films_recycler.layoutManager = layoutManager
    }

    private fun setActorsDetails(personDetails: ActorDetailsModel) {
        oldTitle = mainActivity.title.toString()
        mainActivity.title = personDetails.name
        val profileImageUrl =
            getString(R.string.base_image_url_tmdb).plus(personDetails!!.profilePath)
        Picasso.get().load(profileImageUrl).into(actor_image)
        biography_textView.text = personDetails!!.biography
        setupRV()
    }

    override fun onClick(id: Int) {
        //createFilmDetailsActivity(id)
    }
}