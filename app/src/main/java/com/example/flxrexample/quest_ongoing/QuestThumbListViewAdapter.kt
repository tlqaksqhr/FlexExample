package com.example.flxrexample.quest_ongoing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flxrexample.R
import com.example.flxrexample.quest_model.QuestAuthImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.quest_auth_image_item.view.*

class QuestThumbListViewAdapter(val questAuthEventListener: QuestAuthEventListener) : ListAdapter<QuestAuthImage, QuestThumbListViewAdapter.ViewHolder>(QuestAuthItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quest_auth_image_item,parent,false), questAuthEventListener)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(v: View, questAuthEventListener: QuestAuthEventListener) : RecyclerView.ViewHolder(v){

        val questAuthEventListener = questAuthEventListener

        fun bind(item: QuestAuthImage){
            if(item.pictureURL != "") {
                Picasso.get().load("file://${item.pictureURL}").into(itemView.quest_auth_item_imageview)
            }

            itemView.quest_auth_item_imageview.setOnClickListener {
                questAuthEventListener.addPicture(item)
            }
        }
    }

    class QuestAuthItemDiffCallback: DiffUtil.ItemCallback<QuestAuthImage>() {
        override fun areItemsTheSame(oldItem: QuestAuthImage, newItem: QuestAuthImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: QuestAuthImage, newItem: QuestAuthImage): Boolean {
            return oldItem == newItem
        }
    }
}