package com.sample.rss.screens.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.rss.databinding.LiItemBinding
import com.sample.rss.room.view.RssItemView

class FeedAdapter : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private var data: List<RssItemView> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LiItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getItem(position: Int) = data.getOrNull(position)

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    fun swapData(data: List<RssItemView>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: LiItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemModel: RssItemView?) {
            binding.item = itemModel
        }
    }
}