package com.example.mymoviecatalog.Utils

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

open class CoroutineContextProvieder @Inject constructor() {
    val IO: CoroutineContext by lazy { Dispatchers.IO }
    val MAIN: CoroutineContext by lazy{Dispatchers.Main}
}