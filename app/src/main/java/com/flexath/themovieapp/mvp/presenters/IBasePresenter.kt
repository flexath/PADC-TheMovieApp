package com.flexath.themovieapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner

interface IBasePresenter {
    fun onUIReady(owner:LifecycleOwner)
}