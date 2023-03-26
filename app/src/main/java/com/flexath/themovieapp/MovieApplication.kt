package com.flexath.themovieapp

import android.app.Application
import com.flexath.themovieapp.data.models.MovieBaseModel
import com.flexath.themovieapp.data.models.MovieModelImpl

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MovieModelImpl.initDatabase(applicationContext)
    }
}