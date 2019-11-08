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
import kotlinx.android.synthetic.main.quest_constraint_item.view.*

class QuestConstraintListViewAdapter: ListAdapter<QuestConstraint, QuestConstraintListViewAdapter.ViewHolder>(QuestConstraintDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quest_constraint_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){

        fun bind(item: QuestConstraint){
            itemView.quest_constraint_count.text = "조건 ${adapterPosition}"
            itemView.quest_constraint_text.text = item.content
        }
    }

    class QuestConstraintDiffCallback: DiffUtil.ItemCallback<QuestConstraint>() {
        override fun areItemsTheSame(oldItem: QuestConstraint, newItem: QuestConstraint): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: QuestConstraint, newItem: QuestConstraint): Boolean {
            return oldItem == newItem
        }
    }
}