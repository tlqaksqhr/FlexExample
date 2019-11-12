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
import kotlinx.android.synthetic.main.quest_review_auth_item.view.*

class QuestReviewPictureListViewAdapter : ListAdapter<QuestAuthImage, QuestReviewPictureListViewAdapter.ViewHolder>(QuestAuthItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quest_review_auth_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        fun bind(item: QuestAuthImage) {

            itemView.quest_review_auth_item_text.text = "인증사진 ${adapterPosition + 1}"

            if(item.pictureURL != "") {
                Picasso.get().load("file://${item.pictureURL}").into(itemView.quest_review_auth_item_imageview)
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