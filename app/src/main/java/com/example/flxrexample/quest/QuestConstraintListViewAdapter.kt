package com.example.flxrexample.quest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flxrexample.R
import com.example.flxrexample.quest_model.Quest

class QuestConstraintListViewAdapter(val quests: MutableList<Quest>) : RecyclerView.Adapter<QuestConstraintListViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quest_constraint_item,parent,false))
    }

    override fun getItemCount(): Int {
        return quests.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(quests[position])
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){

        fun bind(item: Quest){

        }
    }
}