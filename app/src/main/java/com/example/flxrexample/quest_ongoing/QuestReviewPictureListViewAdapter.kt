package com.example.flxrexample.quest_ongoing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flxrexample.R

class QuestReviewPictureListViewAdapter(val pictures: MutableList<Int>) : RecyclerView.Adapter<QuestReviewPictureListViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quest_review_auth_item,parent,false))
    }

    override fun getItemCount(): Int {
        return pictures.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pictures[position])
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        fun bind(item: Int) {

        }
    }
}