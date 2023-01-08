package com.flexath.themovieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.themovieapp.R
import com.flexath.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.flexath.themovieapp.viewholders.ShowCaseViewHolder

class ShowCaseAdapter(private val mDelegate : ShowCaseViewHolderDelegate) : RecyclerView.Adapter<ShowCaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowCaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_showcase,parent,false)
        return ShowCaseViewHolder(view,mDelegate)
    }

    override fun onBindViewHolder(holder: ShowCaseViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }
}