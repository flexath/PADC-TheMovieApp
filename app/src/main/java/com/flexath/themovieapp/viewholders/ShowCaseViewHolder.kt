package com.flexath.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.flexath.themovieapp.delegates.ShowCaseViewHolderDelegate

class ShowCaseViewHolder(itemView: View,mDelegate: ShowCaseViewHolderDelegate) : RecyclerView.ViewHolder(itemView) {
    init {
        itemView.setOnClickListener {
            mDelegate.onTapMovieFromShowCase()
        }
    }
}