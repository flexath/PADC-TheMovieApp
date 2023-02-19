package com.flexath.themovieapp.data.vos

import com.google.gson.annotations.SerializedName

data class SpokenLanguageVO(
    @SerializedName("english_name")
    val englishName:String?,

    @SerializedName("iso_639_1")
    val iso:String?,

    @SerializedName("name")
    val name:String?,
)
