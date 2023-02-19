package com.flexath.themovieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.themovieapp.R
import com.flexath.themovieapp.data.vos.ActorVO
import com.flexath.themovieapp.viewholders.ActorViewHolder

class ActorAdapter : RecyclerView.Adapter<ActorViewHolder>() {

    private var mActorList: List<ActorVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_best_actor, parent, false)
        return ActorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        if (mActorList.isNotEmpty()) {
            holder.bindData(mActorList[position])
        }
    }

    override fun getItemCount(): Int {
        return mActorList.count()
    }

    fun setData(actorList: List<ActorVO>) {
        mActorList = actorList
        notifyDataSetChanged()
    }
}