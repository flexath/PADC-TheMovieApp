package com.flexath.themovieapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.themovieapp.data.models.MovieModel
import com.flexath.themovieapp.data.models.MovieModelImpl
import com.flexath.themovieapp.mvp.views.MovieDetailsView

class MovieDetailPresenterImpl : ViewModel(), MovieDetailPresenter {

    private var mViewDetail:MovieDetailsView? = null
    private var mMovieModel:MovieModel = MovieModelImpl

    override fun initView(view: MovieDetailsView) {
        mViewDetail = view
    }

    override fun onUIReady(owner: LifecycleOwner) {}

    override fun onUIReadyInMovieDetails(owner: LifecycleOwner, movieId: Int) {
        mMovieModel.getMovieDetails(movieId = movieId.toString(),
        onFailure = {
            mViewDetail?.showError(it)
        })?.observe(owner){ movie ->
            movie?.let {
                mViewDetail?.showMovieDetails(movie)
            }
        }

        mMovieModel.getCreditByMovie(movieId = movieId.toString(),
        onSuccess = {
            mViewDetail?.showCreditsByMovie(it.first,it.second)
        },
        onFailure = { error ->
            mViewDetail?.showError(error)
        })
    }

    override fun onTapBack() {
        mViewDetail?.navigateBack()
    }
}