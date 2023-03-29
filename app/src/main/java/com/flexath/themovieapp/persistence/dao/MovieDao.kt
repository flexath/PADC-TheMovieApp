package com.flexath.themovieapp.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flexath.themovieapp.data.vos.MovieVO
import io.reactivex.rxjava3.core.Flowable

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieVO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleMovie(movie: MovieVO?)

    @Query("SELECT * FROM movie_table")
    fun getAllMovies(): LiveData<List<MovieVO>>

    @Query("SELECT * FROM movie_table WHERE id = :movieId")
    fun getMovieById(movieId: Int): LiveData<MovieVO?>

    @Query("SELECT * FROM movie_table WHERE id = :movieId")
    fun getMovieByIdOneTime(movieId: Int): MovieVO?

    @Query("SELECT * FROM movie_table WHERE type = :type")
    fun getMoviesByType(type: String): LiveData<List<MovieVO>>

    @Query("DELETE FROM movie_table")
    fun deleteAllMovies()

    @Query("SELECT * FROM movie_table WHERE id = :movieId")
    fun getMovieByIdFlowable(movieId: Int): Flowable<MovieVO?>

    @Query("SELECT * FROM movie_table WHERE type = :type")
    fun getMoviesByTypeFlowable(type: String): Flowable<List<MovieVO>>
}