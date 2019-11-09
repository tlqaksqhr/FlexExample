package com.example.flxrexample.quest_make

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flxrexample.R
import com.example.flxrexample.quest.QuestConstraintListViewAdapter
import com.example.flxrexample.quest_model.QuestConstraint
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.quest_constraint_upload_item.view.*

class QuestConstraintUploadViewAdapter(val questMakeEventListener: QuestMakeEventListener) : ListAdapter<QuestConstraint,
    QuestConstraintUploadViewAdapter.ViewHolder>(QuestConstraintDiffCallback()){

    private var contentTable = HashMap<Int, String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.quest_constraint_upload_item,parent,false),
            questMakeEventListener)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun addContent(idx: Int, content: String) {
        contentTable[idx] = content
    }

    fun getContent() : HashMap<Int, String>{
        return contentTable
    }

    inner class ViewHolder(v: View, questMakeEventListener: QuestMakeEventListener) : RecyclerView.ViewHolder(v) {

        val questMakeEventListener = questMakeEventListener

        fun bind(item: QuestConstraint){

            itemView.quest_constraint_upload_title.text = "조건 ${(adapterPosition+1).toString()}"
            itemView.quest_constraint_upload_text.setText("")

            if(item.pictureURL != "") {
                Picasso.get().load("file://${item.pictureURL}").into(itemView.quest_constraint_upload_imageview)
            }

            itemView.quest_constraint_upload_imageview.setOnClickListener {
                questMakeEventListener.addPicture(item)
            }

            itemView.quest_constraint_upload_text.setOnKeyListener { view, i, keyEvent ->
                val textValue = itemView.quest_constraint_upload_text.text.toString()
                addContent(adapterPosition,textValue)
                true
            }

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