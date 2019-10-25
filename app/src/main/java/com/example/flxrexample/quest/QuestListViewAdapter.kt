package com.example.flxrexample.quest

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flxrexample.R
import com.example.flxrexample.quest_model.Quest
import kotlinx.android.synthetic.main.quest_list_item.view.*

class QuestListViewAdapter : ListAdapter<Quest, QuestListViewAdapter.ViewHolder>(QuestDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quest_list_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){

        private fun onClick(){
            val intent = Intent(itemView.context, QuestViewActivity::class.java)
            itemView.context.startActivity(intent)
        }

        fun bind(item: Quest){

            itemView.quest_item_title.text = item.title
            itemView.quest_item_desc.text = item.desc
            itemView.quest_item_star.text = item.questStar.toString() + " 스타"
            itemView.quest_item_challenge_count.text = item.numOfComplete.toString() + "명 도전중"

            itemView.quest_item_favorite_btn.setOnClickListener {
                onClick()
            }
        }
    }

    class QuestDiffCallback: DiffUtil.ItemCallback<Quest>() {
        override fun areItemsTheSame(oldItem: Quest, newItem: Quest): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Quest, newItem: Quest): Boolean {
            return oldItem == newItem
        }
    }
}