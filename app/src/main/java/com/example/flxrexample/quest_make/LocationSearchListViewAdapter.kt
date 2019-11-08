package com.example.flxrexample.quest_make

import android.location.Address
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flxrexample.R
import kotlinx.android.synthetic.main.location_search_list_item.view.*

class LocationSearchListViewAdapter(val questMakeEventListener: QuestMakeEventListener) : ListAdapter<Address,
        LocationSearchListViewAdapter.ViewHolder>(LocationSearchItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.location_search_list_item, parent, false
            ),
            questMakeEventListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(v: View, questMakeEventListener: QuestMakeEventListener) : RecyclerView.ViewHolder(v) {

        val questMakeEventListener = questMakeEventListener

        fun bind(item: Address){
            itemView.location_item_name.text = item.locality
            itemView.location_item_addr.text = item.getAddressLine(0)
            val marker = questMakeEventListener.addMarker(item)

            itemView.setOnClickListener {
                questMakeEventListener.showMarkerDialog(item,marker)
            }
        }
    }

    // TODO : Fix these check logic
    class LocationSearchItemDiffCallback : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem.locality == newItem.locality && oldItem.latitude == newItem.latitude &&  oldItem.longitude == newItem.longitude
        }

        override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem.locality == newItem.locality && oldItem.latitude == newItem.latitude &&  oldItem.longitude == newItem.longitude
        }
    }
}