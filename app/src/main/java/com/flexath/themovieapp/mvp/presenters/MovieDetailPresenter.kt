package com.flexath.themovieapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.flexath.themovieapp.mvp.views.MovieDetailsView

interface MovieDetailPresenter : IBasePresenter {
    fun initView(view:MovieDetailsView)
    fun onUIReadyInMovieDetails(owner:LifecycleOwner,movieId:Int)
    fun onTapBack()
}