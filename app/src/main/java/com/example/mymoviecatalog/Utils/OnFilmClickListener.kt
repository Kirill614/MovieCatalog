package com.example.mymoviecatalog.Utils

import java.io.FileDescriptor
import java.lang.StringBuilder

interface OnFilmClickListener {
     fun onClick(id: String, description: String)
}