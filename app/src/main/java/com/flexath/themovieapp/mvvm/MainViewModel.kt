package com.flexath.themovieapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flexath.themovieapp.data.models.MovieModel
import com.flexath.themovieapp.data.models.MovieModelImpl
import com.flexath.themovieapp.data.vos.ActorVO
import com.flexath.themovieapp.data.vos.GenreVO
import com.flexath.themovieapp.data.vos.MovieVO

class MainViewModel : ViewModel() {

    private val mMovieModel: MovieModel = MovieModelImpl

    var nowPlayingMoviesLiveData: LiveData<List<MovieVO>>? = null
    var popularMoviesLiveData: LiveData<List<MovieVO>>? = null
    var topRatedMoviesLiveData: LiveData<List<MovieVO>>? = null
    var genresLiveData = MutableLiveData<List<GenreVO>>()
    var moviesByGenreLiveData = MutableLiveData<List<MovieVO>>()
    var actorsLiveData = MutableLiveData<List<ActorVO>>()
    private val mErrorLiveData = MutableLiveData<String>()

    fun getInitialData() {
        nowPlayingMoviesLiveData = mMovieModel.getNowPlayingMovies { mErrorLiveData.postValue(it) }
        popularMoviesLiveData = mMovieModel.getNowPlayingMovies { mErrorLiveData.postValue(it) }
        topRatedMoviesLiveData = mMovieModel.getNowPlayingMovies { mErrorLiveData.postValue(it) }

        mMovieModel.getGenres(
            onSuccess = {
                genresLiveData.postValue(it)
                getMoviesByGenre(0)
            },
            onFailure = {
                mErrorLiveData.postValue(it)
            }
        )
    }

    fun getMoviesByGenre(genrePosition:Int) {
        genresLiveData.value?.getOrNull(genrePosition)?.id?.let {
            mMovieModel.getMoviesByGenre(it.toString(),
            onSuccess = { movieList ->
                moviesByGenreLiveData.postValue(movieList)
            },
            onFailure = { error ->
                mErrorLiveData.postValue(error)
            })
        }
    }


}
