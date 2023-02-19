package com.flexath.themovieapp.network.responses

import com.flexath.themovieapp.data.vos.ActorVO
import com.google.gson.annotations.SerializedName

data class GetCreditsByMovieResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("cast")
    val cast: List<ActorVO>?,

    @SerializedName("crew")
    val crew: List<ActorVO>?

)
