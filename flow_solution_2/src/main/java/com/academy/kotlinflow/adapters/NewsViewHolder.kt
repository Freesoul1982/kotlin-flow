package com.academy.kotlinflow.adapters

import androidx.recyclerview.widget.RecyclerView
import com.academy.kotlinflow.data.News
import com.academy.kotlinflow.databinding.ItemNewsBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class NewsViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
    var formatter: DateFormat = SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault())

    fun bind(item: News) {
        binding.textViewTitle.text = item.title
        binding.textViewAuthor.text = item.author
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = item.timestamp * 1000
        binding.textViewTime.text = formatter.format(calendar.time)
    }
}
