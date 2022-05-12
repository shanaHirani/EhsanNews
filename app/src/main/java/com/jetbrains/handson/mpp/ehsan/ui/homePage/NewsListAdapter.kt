package com.jetbrains.handson.mpp.ehsan.ui.homePage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jetbrains.handson.mpp.ehsan.data.model.News
import com.jetbrains.handson.mpp.ehsan.databinding.NewsListItemBinding

class NewsListAdapter(val onClickListener: OnClickListener):ListAdapter<News,NewsListAdapter.NewsViewHolder>(DiffCallBack) {

    companion object DiffCallBack:DiffUtil.ItemCallback<News>(){
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            if(oldItem.url != null)
                return oldItem.url == newItem.url
            return oldItem.title == newItem.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(NewsListItemBinding
                .inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newItem = getItem(position)
        holder.bind(newItem)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(newItem)
        }
    }

    class NewsViewHolder(private val binding: NewsListItemBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(newItem: News){
            binding.news = newItem
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (news:News) -> Unit) {
        fun onClick(news:News) = clickListener(news)
    }

}