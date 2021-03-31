package com.example.mymoviecatalog.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviecatalog.data.PopMovie
import com.example.mymoviecatalog.R
import com.example.mymoviecatalog.Utils.ItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_view_movie.view.*

class MovieRVAdapter(private val list: List<PopMovie>, private val listener: ItemClickListener) :
    RecyclerView.Adapter<MovieRVAdapter.ItemViewHolder>() {
    private val posterUrl = "https://image.tmdb.org/t/p/w500"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_movie, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val imageUrl = posterUrl.plus(list[position].posterUrl)
        val title = list[position].title
        holder.bind(imageUrl, title)
        holder.itemView.setOnClickListener{
            listener.onClick(list[position].id)
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(url: String, title: String) {
            Picasso.get().load(url).into(itemView.movie_poster)
            itemView.movie_title.text = title
        }

    }
}