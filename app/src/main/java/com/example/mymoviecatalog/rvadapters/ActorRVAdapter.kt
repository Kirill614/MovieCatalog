package com.example.mymoviecatalog.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviecatalog.Network.ApiService
import com.example.mymoviecatalog.data.Person
import com.example.mymoviecatalog.R
import com.example.mymoviecatalog.Utils.ItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_view_actor.view.*
import java.lang.StringBuilder

class ActorRVAdapter(private val list: List<Person>, private val listener: ItemClickListener) :
    RecyclerView.Adapter<ActorRVAdapter.ItemViewHolder>() {
    private var sb = StringBuilder()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_actor, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        sb.append(ApiService.BASE_URL_IMAGE_TMDB).append(list[position].profilePath)
        holder.bind(sb.toString(), list[position].name)
        sb.clear()
        holder.itemView.setOnClickListener {
            listener.onClick(list[position].id)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(url: String, name: String?) {
            Picasso.get().load(url).into(itemView.actors_poster)
            itemView.actors_name.text = name
        }
    }
}