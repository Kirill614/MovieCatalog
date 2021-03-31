package com.example.mymoviecatalog.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviecatalog.R
import com.example.mymoviecatalog.ui.YoutubeFilmFragment
import com.example.mymoviecatalog.data.Film
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.youtube_film_cardview.view.*

class YoutubeRvAdapter(
    private val list: ArrayList<Film>,
    private val listener: YoutubeFilmFragment.OnFilmClickListener
) :
    RecyclerView.Adapter<YoutubeRvAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.youtube_film_cardview, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val description = list[position].snippet?.description
            list[position].snippet?.resourceId?.videoId?.let { listener.onClick(it, description) }
        }
        val posterUrl = list[position].snippet?.thumbnails?.high?.url
        val title = list[position].snippet?.title
        if (posterUrl != null && title != null) holder.bind(posterUrl, title)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(posterUrl: String, filmTitle: String) {
            Picasso.get().load(posterUrl).into(itemView.youtube_film_poster)
            itemView.youtube_film_title.text = filmTitle
        }
    }
}