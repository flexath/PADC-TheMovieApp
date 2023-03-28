package com.flexath.themovieapp.routers

import android.app.Activity
import com.flexath.themovieapp.activities.MovieDetailsActivity
import com.flexath.themovieapp.activities.MovieSearchActivity

fun Activity.navigateToMovieDetailsActivity(movieId:Int) {
    startActivity(MovieDetailsActivity.newIntent(this,movieId))
}

fun Activity.navigateToMovieSearchActivity() {
    startActivity(MovieSearchActivity.newIntent(this))
}