package com.flexath.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flexath.themovieapp.data.vos.MovieVO
import com.flexath.themovieapp.delegates.BannerViewHolderDelegate
import com.flexath.themovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_item_banner.view.*

class BannerViewHolder(itemView: View,mDelegate: BannerViewHolderDelegate) : RecyclerView.ViewHolder(itemView) {

    private var mMovie:MovieVO? = null

    init {
        itemView.setOnClickListener {
            mMovie?.let { movie ->
                mDelegate.onTapMovieFromBanner(movie.id)
            }
        }
    }

    fun bindData(movieResult:MovieVO) {
        mMovie = movieResult

        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${movieResult.posterPath}")
            .into(itemView.ivBannerImage)

        itemView.tvBannerMovieName.text = movieResult.title
    }
}