package com.flexath.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flexath.themovieapp.data.vos.MovieVO
import com.flexath.themovieapp.delegates.MovieViewHolderDelegate
import com.flexath.themovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_movie.view.*

class MovieViewHolder(itemView: View,delegate:MovieViewHolderDelegate) : RecyclerView.ViewHolder(itemView) {

    private var mMovie:MovieVO? = null

    init {
        itemView.setOnClickListener {
            mMovie?.let { movie ->
                delegate.onTapMovie(movie.id)
            }

        }
    }

    fun bindData(movie:MovieVO) {
        mMovie = movie

        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(itemView.ivMovieImage)

        itemView.tvMovieName.text = movie.title
        itemView.tvMovieRating.text = movie.voteAverage.toString()
        itemView.rbMovieStar.rating = movie.getRatingBaseOnFiveStars()
    }
}