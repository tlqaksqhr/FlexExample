package com.example.flxrexample.coupon

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flxrexample.R
import kotlinx.android.synthetic.main.coupon_item.view.*

class CouponListAdapter : ListAdapter<String, CouponListAdapter.ViewHolder>(CouponListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.coupon_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        fun bind(item: String) {
            itemView.coupon_item_title.text = item
            itemView.setOnClickListener {
                val intent = Intent(itemView.context,CouponViewActivity::class.java)
                intent.putExtra("COUPON_NAME",item)
                startActivity(itemView.context,intent,null)
            }
        }

    }

    class CouponListDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}