package com.flexath.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.flexath.themovieapp.data.vos.ProductionCompanyVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductionCompanyTypeConverter {
    @TypeConverter
    fun toString(productionCompanies: List<ProductionCompanyVO>?) :String {
        return Gson().toJson(productionCompanies)
    }

    @TypeConverter
    fun toProductionCompanies(jsonString:String) : List<ProductionCompanyVO>? {
        val productionCompanyType = object : TypeToken<List<ProductionCompanyVO>?>(){}.type
        return Gson().fromJson(jsonString,productionCompanyType)
    }
}