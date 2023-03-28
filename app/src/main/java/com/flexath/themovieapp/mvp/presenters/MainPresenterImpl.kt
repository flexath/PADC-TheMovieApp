package com.flexath.themovieapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.themovieapp.data.vos.GenreVO
import com.flexath.themovieapp.interactors.MovieInteractor
import com.flexath.themovieapp.interactors.MovieInteractorImpl
import com.flexath.themovieapp.mvp.views.MainView

class MainPresenterImpl : ViewModel(), MainPresenter {

    private var mView:MainView?= null

    // Interactor
    private val mMovieInteractor:MovieInteractor = MovieInteractorImpl

    private var mGenres: List<GenreVO>? = listOf()

    override fun initView(view: MainView) {
        mView = view
    }

    override fun onUIReady(owner: LifecycleOwner) {
        mMovieInteractor.getNowPlayingMovies { error ->
            mView?.showError(error)
        }?.observe(owner){
            mView?.showNowPlayingMovies(it)
        }

        mMovieInteractor.getNowPlayingMovies { error ->
            mView?.showError(error)
        }?.observe(owner){
            mView?.showPopularMovies(it)
        }

        mMovieInteractor.getNowPlayingMovies { error ->
            mView?.showError(error)
        }?.observe(owner){
            mView?.showTopRatedMovies(it)
        }

        mMovieInteractor.getGenres(onSuccess = {
            mGenres = it
            mView?.showGenres(it)

            it.firstOrNull()?.id.let { genreId ->
                onTapGenre(genreId ?: 0)
            }
        },
        onFailure = {
            mView?.showError(it)
        })

        mMovieInteractor.getActors(
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
            mMovieInteractor.getMoviesByGenre(
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