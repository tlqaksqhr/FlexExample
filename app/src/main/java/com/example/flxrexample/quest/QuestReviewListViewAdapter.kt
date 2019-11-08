package com.example.flxrexample.quest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flxrexample.R
import com.example.flxrexample.quest_model.Quest
import com.example.flxrexample.quest_model.QuestConstraint
import com.example.flxrexample.quest_model.Review
import kotlinx.android.synthetic.main.quest_review_item.view.*

class QuestReviewListViewAdapter : ListAdapter<Review,QuestReviewListViewAdapter.ViewHolder>(ReviewDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quest_review_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){

        fun bind(item: Review){
            itemView.quest_review_item_rating.rating = item.rating
            itemView.quest_review_item_content.text = item.content
        }
    }

    class ReviewDiffCallback: DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem == newItem
        }
    }
}