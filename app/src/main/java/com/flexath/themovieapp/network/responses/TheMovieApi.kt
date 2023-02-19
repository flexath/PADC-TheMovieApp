package com.flexath.themovieapp.network.responses

import com.flexath.themovieapp.data.vos.MovieVO
import com.flexath.themovieapp.utils.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TheMovieApi {
    @GET(API_GET_NOW_PLAYING)
    fun getNowPlayingMovies(
        @Query(PARAM_API_KEY) api_key:String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page:Int = 1,
    ) : Call<MovieListResponse>

    @GET(API_GET_POPULAR)
    fun getPopularMovies(
        @Query(PARAM_API_KEY) api_key:String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page:Int = 1,
    ) : Call<MovieListResponse>

    @GET(API_GET_TOP_RATED)
    fun getTopRatedMovies(
        @Query(PARAM_API_KEY) api_key:String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page:Int = 1,
    ) : Call<MovieListResponse>

    @GET(API_GET_GENRES)
    fun getGenres(
        @Query(PARAM_API_KEY) api_key:String = MOVIE_API_KEY,
    ) : Call<GetGenresResponse>

    @GET(API_GET_MOVIES_BY_GENRE)
    fun getMoviesByGenre(
        @Query(PARAM_API_KEY) api_key:String = MOVIE_API_KEY,
        @Query(PARAM_GENRE_ID) genreId:String
    ) : Call<MovieListResponse>

    @GET(API_GET_ACTORS)
    fun getActors(
        @Query(PARAM_API_KEY) api_key:String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page:Int = 1,
    ) : Call<GetActorsResponse>

    @GET("$API_GET_MOVIE_DETAILS/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId:String?,
        @Query(PARAM_API_KEY) api_key:String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page:Int = 1,
    ) : Call<MovieVO>

    @GET("$API_GET_CREDIT_BY_MOVIE/{movie_id}/credits")
    fun getCreditByMovie(
        @Path("movie_id") movieId:String?,
        @Query(PARAM_API_KEY) api_key:String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page:Int = 1,
    ) : Call<GetCreditsByMovieResponse>
}