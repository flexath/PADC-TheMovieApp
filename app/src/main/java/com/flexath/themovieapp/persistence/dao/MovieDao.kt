package com.flexath.themovieapp.persistence.dao

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
    fun getAllMovies():List<MovieVO>

    @Query("SELECT * FROM movie_table WHERE id = :movieId")
    fun getMovieById(movieId:Int): MovieVO?

    @Query("SELECT * FROM movie_table WHERE type = :type")
    fun getMoviesByType(type:String):List<MovieVO>

    @Query("DELETE FROM movie_table")
    fun deleteAllMovies()
}