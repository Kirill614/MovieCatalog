package com.example.mymoviecatalog.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviecatalog.data.Movie
import com.example.mymoviecatalog.R
import com.example.mymoviecatalog.Utils.ItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.actor_details_film_cardview2.view.*

class ActorsFilmsAdapter(private val list: ArrayList<Movie>, private val listener: ItemClickListener) :
    RecyclerView.Adapter<ActorsFilmsAdapter.ItemViewHolder>() {
    private val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.actor_details_film_cardview2, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val title = list[position].movieTitle
        val url = BASE_IMAGE_URL.plus(list[position].posterPath)
        holder.itemView.setOnClickListener{
            listener.onClick(list[position].id)
        }
        holder.bind(url,title)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(url: String, title: String?) {
            Picasso.get().load(url).into(itemView.actor_details_film_poster)
            itemView.actor_details_film_title.text = title
        }
    }
}