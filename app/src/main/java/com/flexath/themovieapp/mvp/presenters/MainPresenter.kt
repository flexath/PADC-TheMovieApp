package com.flexath.themovieapp.mvp.presenters

import com.flexath.themovieapp.delegates.BannerViewHolderDelegate
import com.flexath.themovieapp.delegates.MovieViewHolderDelegate
import com.flexath.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.flexath.themovieapp.mvp.views.MainView

interface MainPresenter : IBasePresenter, BannerViewHolderDelegate, ShowCaseViewHolderDelegate,
    MovieViewHolderDelegate {

        fun initView(view: MainView)
        fun onTapGenre(genrePosition:Int)
}