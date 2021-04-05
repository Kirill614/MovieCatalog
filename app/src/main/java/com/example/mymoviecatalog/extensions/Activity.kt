package com.example.mymoviecatalog.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.mymoviecatalog.di.Factory

inline fun <reified T : ViewModel> AppCompatActivity.viewModel(factory: Factory): T {
    return ViewModelProviders.of(this, factory)[T::class.java]
}