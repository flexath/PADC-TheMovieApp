package com.flexath.themovieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.themovieapp.R
import com.flexath.themovieapp.data.vos.MovieVO
import com.flexath.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.flexath.themovieapp.viewholders.ShowCaseViewHolder

class ShowCaseAdapter(private val mDelegate: ShowCaseViewHolderDelegate) :
    RecyclerView.Adapter<ShowCaseViewHolder>() {

    private var mMovieList: List<MovieVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowCaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_showcase, parent, false)
        return ShowCaseViewHolder(view, mDelegate)
    }

    override fun onBindViewHolder(holder: ShowCaseViewHolder, position: Int) {
        if (mMovieList.isNotEmpty()) {
            holder.bindData(mMovieList[position])
        }
    }

    override fun getItemCount(): Int {
        return mMovieList.count()
    }

    fun setNewData(movieList: List<MovieVO>) {
        mMovieList = movieList
        notifyDataSetChanged()
    }
}