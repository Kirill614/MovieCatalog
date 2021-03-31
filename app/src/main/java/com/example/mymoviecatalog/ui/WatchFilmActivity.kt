package com.example.mymoviecatalog.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mymoviecatalog.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_watch_film.*

class WatchFilmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_film)
        supportActionBar?.hide()
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        lifecycle.addObserver(youtube_player)
        val bundle = intent.getBundleExtra("bundle")
        val filmId = bundle?.getString("id").toString()
        val description = bundle?.getString("description")
        descr.text = description
        youtube_player.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(filmId, 0f)
            }
        })
    }
}