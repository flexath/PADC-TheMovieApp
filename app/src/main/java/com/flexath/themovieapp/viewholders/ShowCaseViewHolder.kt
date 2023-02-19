package com.flexath.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flexath.themovieapp.data.vos.MovieVO
import com.flexath.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.flexath.themovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_movie.view.*
import kotlinx.android.synthetic.main.view_holder_showcase.view.*

class ShowCaseViewHolder(itemView: View,mDelegate: ShowCaseViewHolderDelegate) : RecyclerView.ViewHolder(itemView) {

    private var mMovie:MovieVO? = null

    init {
        itemView.setOnClickListener {
            mMovie?.let { movie ->
                mDelegate.onTapMovieFromShowCase(movie.id)
            }
        }
    }

    fun bindData(movie:MovieVO) {
        mMovie = movie

        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(itemView.ivShowCase)

        itemView.tvShowCaseMovieName.text = movie.title
        itemView.tvShowCaseMovieDate.text = movie.releaseDate
    }
}