package com.flexath.themovieapp.data.models

import com.flexath.themovieapp.data.vos.ActorVO
import com.flexath.themovieapp.data.vos.GenreVO
import com.flexath.themovieapp.data.vos.MovieVO
import com.flexath.themovieapp.network.dataagents.MovieDataAgent
import com.flexath.themovieapp.network.dataagents.RetrofitDataAgentImpl

object MovieModelImpl : MovieModel {

    private val mMovieDataAgent:MovieDataAgent = RetrofitDataAgentImpl

    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieDataAgent.getNowPlayingMovies(onSuccess = onSuccess , onFailure = onFailure)
    }

    override fun getPopularMovies(onSuccess: (List<MovieVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieDataAgent.getPopularMovies(onSuccess = onSuccess , onFailure = onFailure)
    }

    override fun getTopRatedMovies(onSuccess: (List<MovieVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieDataAgent.getTopRatedMovies(onSuccess = onSuccess , onFailure = onFailure)
    }

    override fun getGenres(onSuccess: (List<GenreVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieDataAgent.getGenres(onSuccess = onSuccess , onFailure = onFailure)
    }

    override fun getMoviesByGenre(genreId: String, onSuccess: (List<MovieVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieDataAgent.getMoviesByGenres(genreId,onSuccess = onSuccess , onFailure = onFailure)
    }

    override fun getActors(onSuccess: (List<ActorVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieDataAgent.getActors(onSuccess = onSuccess , onFailure = onFailure)
    }

    override fun getMovieDetails(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieDataAgent.getMovieDetails(movieId,onSuccess = onSuccess , onFailure = onFailure)
    }

    override fun getCreditByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieDataAgent.getCreditByMovie(movieId,onSuccess = onSuccess , onFailure = onFailure)
    }
}