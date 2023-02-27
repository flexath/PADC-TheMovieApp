package com.flexath.themovieapp.data.models

import android.content.Context
import com.flexath.themovieapp.data.vos.*
import com.flexath.themovieapp.network.dataagents.MovieDataAgent
import com.flexath.themovieapp.network.dataagents.RetrofitDataAgentImpl
import com.flexath.themovieapp.persistence.MovieDatabase

object MovieModelImpl : MovieModel {

    private val mMovieDataAgent:MovieDataAgent = RetrofitDataAgentImpl
    private var mMovieDatabase:MovieDatabase? = null

    fun initDatabase(context: Context) {
        mMovieDatabase = MovieDatabase.getDBInstance(context)
    }

    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        onSuccess(mMovieDatabase?.movieDao()?.getMoviesByType(NOW_PLAYING) ?: listOf())
        mMovieDataAgent.getNowPlayingMovies(onSuccess = {
            it.forEach { movie ->
                movie.type = NOW_PLAYING
            }
            mMovieDatabase?.movieDao()?.insertMovies(it)
            onSuccess(it)
        } , onFailure = onFailure)
    }

    override fun getPopularMovies(onSuccess: (List<MovieVO>) -> Unit, onFailure: (String) -> Unit) {
        onSuccess(mMovieDatabase?.movieDao()?.getMoviesByType(POPULAR) ?: listOf())
        mMovieDataAgent.getPopularMovies(onSuccess = {
            it.forEach { movie ->
                movie.type = POPULAR
            }
            mMovieDatabase?.movieDao()?.insertMovies(it)
            onSuccess(it)
        } , onFailure = onFailure)
    }

    override fun getTopRatedMovies(onSuccess: (List<MovieVO>) -> Unit, onFailure: (String) -> Unit) {
        onSuccess(mMovieDatabase?.movieDao()?.getMoviesByType(TOP_RATED) ?: listOf())
        mMovieDataAgent.getTopRatedMovies(onSuccess = {
            it.forEach { movie ->
                movie.type = TOP_RATED
            }
            mMovieDatabase?.movieDao()?.insertMovies(it)
            onSuccess(it)
        } , onFailure = onFailure)
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
        val movieDatabase = mMovieDatabase?.movieDao()?.getMovieById(movieId.toInt())
        movieDatabase?.let {
            onSuccess(it)
        }
        mMovieDataAgent.getMovieDetails(movieId,onSuccess = {
            val movieFromDatabase = mMovieDatabase?.movieDao()?.getMovieById(movieId.toInt())
            it.type = movieFromDatabase?.type
            mMovieDatabase?.movieDao()?.insertSingleMovie(it)
            onSuccess(it)
        } , onFailure = onFailure)
    }

    override fun getCreditByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieDataAgent.getCreditByMovie(movieId,onSuccess = onSuccess , onFailure = onFailure)
    }
}