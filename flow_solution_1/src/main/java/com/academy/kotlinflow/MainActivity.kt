package com.academy.kotlinflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.academy.kotlinflow.adapters.NewsAdapter
import com.academy.kotlinflow.data.NewsRepository
import com.academy.kotlinflow.databinding.ActivityMainBinding
import com.academy.kotlinflow.viewmodels.NewsViewModel
import com.academy.kotlinflow.viewmodels.NewsViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: NewsViewModel
    private var newsAdapter: NewsAdapter? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this, NewsViewModelFactory(NewsRepository(this))).get(NewsViewModel::class.java)
        newsAdapter = NewsAdapter(this)
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewNews.layoutManager = layoutManager
        binding.recyclerViewNews.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        binding.recyclerViewNews.adapter = newsAdapter
        viewModel.getNews().observe(this, { news ->
            newsAdapter?.setItems(ArrayList(news))
        })
        viewModel.getFetchingStatus().observe(this, {
            updateFetchUI(it)
        })
        viewModel.fetchNews()
    }

    private fun updateFetchUI(isFetching: Boolean) {
        if(isFetching) {
            binding.textViewStatus.text = getString(R.string.button_refreshing)
            binding.textViewStatus.visibility = View.VISIBLE
            binding.progressBar.visibility = View.VISIBLE
        }
        else {
            binding.textViewStatus.text = ""
            binding.textViewStatus.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}