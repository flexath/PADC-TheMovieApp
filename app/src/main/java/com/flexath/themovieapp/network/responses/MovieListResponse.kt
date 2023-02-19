package com.flexath.themovieapp.network.responses

import com.flexath.themovieapp.data.vos.DateVO
import com.flexath.themovieapp.data.vos.MovieVO
import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("page")
    val page:Int?,
    @SerializedName("dates")
    val dates:DateVO?,
    @SerializedName("results")
    val results:List<MovieVO>?
)
