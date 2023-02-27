package com.flexath.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.flexath.themovieapp.data.vos.GenreVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreListTypeConverter {
    @TypeConverter
    fun toString(genreList: List<GenreVO>?) :String {
        return Gson().toJson(genreList)
    }

    @TypeConverter
    fun toGenreList(jsonString:String) : List<GenreVO>? {
        val genreListType = object : TypeToken<List<GenreVO>?>(){}.type
        return Gson().fromJson(jsonString,genreListType)
    }
}