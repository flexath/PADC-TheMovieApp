package com.flexath.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreIdsTypeConverter {
    @TypeConverter
    fun toString(genreIds: List<Int>?) :String {
        return Gson().toJson(genreIds)
    }

    @TypeConverter
    fun toGenreIds(jsonString:String) : List<Int>? {
        val genreIdVOType = object : TypeToken<List<Int>?>(){}.type
        return Gson().fromJson(jsonString,genreIdVOType)
    }
}