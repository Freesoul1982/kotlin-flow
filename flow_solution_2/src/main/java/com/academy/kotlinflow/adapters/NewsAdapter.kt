package com.academy.kotlinflow.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.academy.kotlinflow.data.News
import com.academy.kotlinflow.databinding.ItemNewsBinding

class NewsAdapter(val context: Context): RecyclerView.Adapter<NewsViewHolder>() {
    var news: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = news[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    fun setItems(news: ArrayList<News>) {
        this.news = news
        notifyDataSetChanged()
    }
}