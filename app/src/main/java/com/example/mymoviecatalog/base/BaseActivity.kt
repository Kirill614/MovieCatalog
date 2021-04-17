package com.example.mymoviecatalog.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mymoviecatalog.di.DaggerAppComponent
import com.example.mymoviecatalog.di.Factory
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: Factory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component = DaggerAppComponent.builder().application(this).build()
        component.inject(this)
    }
}