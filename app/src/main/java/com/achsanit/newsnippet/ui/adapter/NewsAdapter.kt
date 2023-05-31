package com.achsanit.newsnippet.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.achsanit.newsnippet.R
import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.databinding.ItemNewsBinding
import com.achsanit.newsnippet.utils.setShimmerPlaceholder

class NewsAdapter(
    private val onClickItem: (NewsEntity) -> Unit
): RecyclerView.Adapter<NewsAdapter.ViewHolder>(){
    inner class ViewHolder(private val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: NewsEntity) {
            with(binding) {
                tvNewsTitle.text = data.title
                if (data.urlToImage.isNotEmpty()) {
                    ivNews.load(data.urlToImage) {
                        setShimmerPlaceholder()
                    }
                } else {
                    ivNews.load(R.drawable.no_image) {
                        setShimmerPlaceholder()
                    }
                }
                tvSources.text = data.source

                root.setOnClickListener {
                    onClickItem(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemNewsBinding.inflate(
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