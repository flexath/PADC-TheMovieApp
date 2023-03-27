package com.flexath.themovieapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flexath.themovieapp.data.models.MovieModel
import com.flexath.themovieapp.data.models.MovieModelImpl
import com.flexath.themovieapp.data.vos.ActorVO
import com.flexath.themovieapp.data.vos.MovieVO

class MovieDetailsModel : ViewModel() {

    private val mMovieModel:MovieModel = MovieModelImpl

    var movieDetailsLiveData:LiveData<MovieVO?>? = null
    var castLiveData = MutableLiveData<List<ActorVO>?>()
    var crewLiveData = MutableLiveData<List<ActorVO>?>()
    private val mErrorLiveData = MutableLiveData<String>()

    fun getInitialDetailsData(movieId:Int) {
        movieDetailsLiveData = mMovieModel.getMovieDetails(movieId = movieId.toString()) { mErrorLiveData.postValue(it) }

        mMovieModel.getCreditByMovie(movieId = movieId.toString(),
        onSuccess = {
            castLiveData.postValue(it.first)
            crewLiveData.postValue(it.second)
        },
        onFailure = {
            mErrorLiveData.postValue(it)
        })
    }
}
