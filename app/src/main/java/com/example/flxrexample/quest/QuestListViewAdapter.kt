package com.example.flxrexample.quest

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorSpace
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flxrexample.R
import com.example.flxrexample.quest_model.Quest
import kotlinx.android.synthetic.main.quest_list_item.view.*

class QuestListViewAdapter(val questMainEventListener: QuestMainEventListener) : ListAdapter<Quest, QuestListViewAdapter.ViewHolder>(QuestDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quest_list_item,parent,false),questMainEventListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(v: View, questMainEventListener: QuestMainEventListener) : RecyclerView.ViewHolder(v){

        lateinit var questMainEventListener: QuestMainEventListener

        init{
            this.questMainEventListener = questMainEventListener
        }

        fun bind(item: Quest){
            itemView.quest_item_title.text = item.title
            itemView.quest_item_desc.text = item.desc
            itemView.quest_item_star.text = item.questStar.toString() + " 스타"
            itemView.quest_item_challenge_count.text = item.numOfComplete.toString() + "명 도전중"

            itemView.quest_item_cardview.strokeColor = Color.rgb(0xd6,0xd6,0xd6)
            itemView.quest_item_cardview.strokeWidth = 3

            val marker = questMainEventListener.addMarker(item)

            itemView.setOnClickListener {
                if(adapterPosition != RecyclerView.NO_POSITION){
                    questMainEventListener.showMarkerDialog(item, marker)
                }
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