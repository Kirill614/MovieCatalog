package com.example.mymoviecatalog.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviecatalog.data.FoundMovie
import com.example.mymoviecatalog.R
import com.example.mymoviecatalog.Utils.ItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.search_row.view.*

class SearchFilmAdapter(private val list: ArrayList<FoundMovie>,private val listener: ItemClickListener) :
    RecyclerView.Adapter<SearchFilmAdapter.ItemViewHolder>() {
    private val baseImageUrl = "https://image.tmdb.org/t/p/w500"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_row, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder,position: Int) {
        val imageUrl = baseImageUrl.plus(list[position].posterPath)
        val title = list[position].title
        holder.bind(imageUrl,title)
        holder.itemView.setOnClickListener{
            listener.onClick(list[position].id)
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(imageUrl: String,title: String){
            Picasso.get().load(imageUrl).into(itemView.search_film_poster1)
            itemView.foundFilm_title1.text = title
        }



    }
}