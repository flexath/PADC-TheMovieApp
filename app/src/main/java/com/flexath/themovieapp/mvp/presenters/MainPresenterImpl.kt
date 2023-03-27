package com.flexath.themovieapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.themovieapp.data.models.MovieModel
import com.flexath.themovieapp.data.models.MovieModelImpl
import com.flexath.themovieapp.data.vos.GenreVO
import com.flexath.themovieapp.mvp.views.MainView

class MainPresenterImpl : ViewModel(), MainPresenter {

    private var mView:MainView?= null
    private val mMovieModel:MovieModel = MovieModelImpl
    private var mGenres: List<GenreVO>? = listOf()

    override fun initView(view: MainView) {
        mView = view
    }

    override fun onUIReady(owner: LifecycleOwner) {
        mMovieModel.getNowPlayingMovies { error ->
            mView?.showError(error)
        }?.observe(owner){
            mView?.showNowPlayingMovies(it)
        }

        mMovieModel.getNowPlayingMovies { error ->
            mView?.showError(error)
        }?.observe(owner){
            mView?.showPopularMovies(it)
        }

        mMovieModel.getNowPlayingMovies { error ->
            mView?.showError(error)
        }?.observe(owner){
            mView?.showTopRatedMovies(it)
        }

        mMovieModel.getGenres(onSuccess = {
            mGenres = it
            mView?.showGenres(it)

            it.firstOrNull()?.id.let { genreId ->
                onTapGenre(genreId ?: 0)
            }
        },
        onFailure = {
            mView?.showError(it)
        })

        mMovieModel.getActors(
            onSuccess = {
                mView?.showActors(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapMovieFromBanner(movieId: Int) {
        mView?.navigateToMovieDetailScreen(movieId)
    }

    override fun onTapMovieFromShowCase(movieId: Int) {
        mView?.navigateToMovieDetailScreen(movieId)
    }

    override fun onTapMovie(movieId: Int) {
        mView?.navigateToMovieDetailScreen(movieId)
    }

    override fun onTapGenre(genrePosition: Int) {

        mGenres?.getOrNull(genrePosition)?.id.let { genreId ->
            mMovieModel.getMoviesByGenre(
                genreId = genreId.toString(),
                onSuccess = {
                    mView?.showMoviesByGenre(it)
                },
                onFailure = {
                    mView?.showError(it)
                }
            )
        }
    }
}