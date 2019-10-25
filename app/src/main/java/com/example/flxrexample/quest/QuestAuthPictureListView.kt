package com.example.flxrexample.quest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flxrexample.R

class QuestAuthPictureListView(val authPictures: MutableList<Integer>) : RecyclerView.Adapter<QuestAuthPictureListView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quest_auth_image_item,parent,false))
    }

    override fun getItemCount(): Int {
        return authPictures.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(authPictures[position])
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        fun bind(item: Integer){

        }
    }
}