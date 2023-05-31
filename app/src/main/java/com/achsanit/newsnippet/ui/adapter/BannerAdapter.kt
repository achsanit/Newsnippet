package com.achsanit.newsnippet.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.databinding.ItemTopHeadlinesBinding
import com.achsanit.newsnippet.utils.setShimmerPlaceholder

class BannerAdapter(
    private val onClickItem: (NewsEntity) -> Unit
): RecyclerView.Adapter<BannerAdapter.ViewHolder>() {
    inner class ViewHolder(
        private val binding: ItemTopHeadlinesBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NewsEntity) {
            with(binding) {
                tvNewsTitle.text = data.title
                ivNewsBanner.load(data.urlToImage) {
                    setShimmerPlaceholder()
                }

                root.setOnClickListener {
                    onClickItem(data)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemTopHeadlinesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(view)
    }

    private val listNews = ArrayList<NewsEntity>()

    fun submitData(data: List<NewsEntity>) {
        listNews.clear()
        listNews.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listNews[position])
    }
}