package com.flexath.themovieapp.mvp.views

import com.flexath.themovieapp.data.vos.ActorVO
import com.flexath.themovieapp.data.vos.GenreVO
import com.flexath.themovieapp.data.vos.MovieVO

interface MainView : BaseView {
    fun showNowPlayingMovies(movieList:List<MovieVO>)
    fun showPopularMovies(movieList:List<MovieVO>)
    fun showTopRatedMovies(movieList:List<MovieVO>)
    fun showMoviesByGenre(movieList:List<MovieVO>)
    fun showGenres(genreList:List<GenreVO>)
    fun showActors(actorList:List<ActorVO>)
    fun navigateToMovieDetailScreen(movieId:Int)
}