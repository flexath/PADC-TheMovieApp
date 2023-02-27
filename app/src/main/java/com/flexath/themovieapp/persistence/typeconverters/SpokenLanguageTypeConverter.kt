package com.flexath.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.flexath.themovieapp.data.vos.SpokenLanguageVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SpokenLanguageTypeConverter {
    @TypeConverter
    fun toString(spokenLanguages: List<SpokenLanguageVO>?) :String {
        return Gson().toJson(spokenLanguages)
    }

    @TypeConverter
    fun toSpokenLanguages(jsonString:String) : List<SpokenLanguageVO>? {
        val spokenLanguageType = object : TypeToken<List<SpokenLanguageVO>?>(){}.type
        return Gson().fromJson(jsonString,spokenLanguageType)
    }
}