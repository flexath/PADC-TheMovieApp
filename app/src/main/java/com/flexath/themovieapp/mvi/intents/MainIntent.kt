package com.flexath.themovieapp.mvi.intents

import com.flexath.themovieapp.mvi.mvibase.MVIIntent

sealed class MainIntent : MVIIntent {
    class LoadMoviesByGenreIntent(val genrePosition:Int) : MainIntent()
    object LoadAllHomePageData : MainIntent()
}