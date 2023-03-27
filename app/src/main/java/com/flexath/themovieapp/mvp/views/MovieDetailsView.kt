package com.flexath.themovieapp.mvp.views

import com.flexath.themovieapp.data.vos.ActorVO
import com.flexath.themovieapp.data.vos.MovieVO

interface MovieDetailsView : BaseView {
    fun showMovieDetails(movie:MovieVO)
    fun showCreditsByMovie(castList:List<ActorVO>,crewList:List<ActorVO>)
    fun navigateBack()
}