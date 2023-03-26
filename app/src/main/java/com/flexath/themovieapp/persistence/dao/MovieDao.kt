package com.flexath.themovieapp.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flexath.themovieapp.data.vos.MovieVO

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies:List<MovieVO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleMovie(movie: MovieVO?)

    @Query("SELECT * FROM movie_table")
    fun getAllMovies():LiveData<List<MovieVO>>

    @Query("SELECT * FROM movie_table WHERE id = :movieId")
    fun getMovieById(movieId:Int): LiveData<MovieVO?>

    @Query("SELECT * FROM movie_table WHERE id = :movieId")
    fun getMovieByIdOneTime(movieId:Int): MovieVO?

    @Query("SELECT * FROM movie_table WHERE type = :type")
    fun getMoviesByType(type:String):LiveData<List<MovieVO>>

    @Query("DELETE FROM movie_table")
    fun deleteAllMovies()
}