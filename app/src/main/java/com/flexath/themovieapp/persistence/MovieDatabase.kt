package com.flexath.themovieapp.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.flexath.themovieapp.data.vos.MovieVO
import com.flexath.themovieapp.persistence.dao.MovieDao

@Database([MovieVO::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        private var movieDatabase:MovieDatabase? = null
        const val dbName = "MovieDB"

        fun getDBInstance(context: Context) : MovieDatabase? {
            when(movieDatabase) {
                null -> {
                    movieDatabase = Room.databaseBuilder(context,MovieDatabase::class.java, dbName)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return movieDatabase
        }
    }
}