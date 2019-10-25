package com.example.flxrexample.quest

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flxrexample.R
import com.example.flxrexample.quest_model.Quest
import kotlinx.android.synthetic.main.quest_list_item.view.*

class QuestListViewAdapter(val quests: MutableList<Quest>) : RecyclerView.Adapter<QuestListViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quest_list_item,parent,false))
    }

    override fun getItemCount(): Int {
        return quests.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(quests[position])
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){

        private fun onClick(){
            val intent = Intent(itemView.context, QuestViewActivity::class.java)
            itemView.context.startActivity(intent)
        }

        fun bind(item: Quest){
            itemView.quest_item_favorite_btn.setOnClickListener {
                onClick()
            }
        }
    }
}