package com.flexath.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.flexath.themovieapp.data.vos.CollectionVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CollectionTypeConverter {

    @TypeConverter
    fun toString(collection: CollectionVO?) :String {
        return Gson().toJson(collection)
    }

    @TypeConverter
    fun toCollection(jsonString:String) : CollectionVO? {
        val collectionVOType = object : TypeToken<CollectionVO?>(){}.type
        return Gson().fromJson(jsonString,collectionVOType)
    }
}