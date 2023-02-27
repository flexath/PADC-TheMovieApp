package com.flexath.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.flexath.themovieapp.data.vos.ProductionCountryVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductionCountryTypeConverter {
    @TypeConverter
    fun toString(productionCountries: List<ProductionCountryVO>?) :String {
        return Gson().toJson(productionCountries)
    }

    @TypeConverter
    fun toProductionCountries(jsonString:String) : List<ProductionCountryVO>? {
        val productionCountryType = object : TypeToken<List<ProductionCountryVO>?>(){}.type
        return Gson().fromJson(jsonString,productionCountryType)
    }
}