package com.flexath.themovieapp.data.vos

import com.google.gson.annotations.SerializedName

data class ProductionCountryVO(
    @SerializedName("iso_3166_1")
    val iso:String?,

    @SerializedName("name")
    val name:String?,
)
