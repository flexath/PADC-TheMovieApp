package com.flexath.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.flexath.themovieapp.delegates.MovieViewHolderDelegate

class MovieViewHolder(itemView: View,delegate:MovieViewHolderDelegate) : RecyclerView.ViewHolder(itemView) {
    init {
        itemView.setOnClickListener {
            delegate.onTapMovie()
        }
    }
}