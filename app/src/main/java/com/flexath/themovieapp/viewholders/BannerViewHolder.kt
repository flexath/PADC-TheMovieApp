package com.flexath.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.flexath.themovieapp.delegates.BannerViewHolderDelegate

class BannerViewHolder(itemView: View,mDelegate: BannerViewHolderDelegate) : RecyclerView.ViewHolder(itemView) {
    init {
        itemView.setOnClickListener {
            mDelegate.onTapMovieFromBanner()
        }
    }
}