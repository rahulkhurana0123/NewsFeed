package com.example.newsfeed.doubtnut.view

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.newsfeed.doubtnut.R
import com.example.newsfeed.doubtnut.databinding.NewsFeedItemLayoutBinding
import com.example.newsfeed.doubtnut.viewModel.NewsFeedViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_feed_item_layout.view.*

class NewsFeedAdapter(
    private val viewModel: NewsFeedViewModel,
    private val context: Context,
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): NewsFeedViewHolder {
        return NewsFeedViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.news_feed_item_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return try {
            viewModel.newsFeedLiveData.value?.articles?.size!!
        } catch (e: KotlinNullPointerException) {
            0
        }
    }

    override fun onBindViewHolder(viewHolder: NewsFeedViewHolder, position: Int) {
        viewHolder.itemLayoutBinding.viewModel = viewModel
        viewHolder.itemLayoutBinding.position = position
        if (viewModel.getArticleAtIndex(position) != null && !viewModel.getArticleAtIndex(position)?.urlToImage.isNullOrBlank()) {
            Picasso.with(viewHolder.itemView.context).load(viewModel.getArticleAtIndex(position)?.urlToImage)
                .into(viewHolder.itemView.news_feed_item_imv)
        }

        viewHolder.itemView.setOnClickListener { clickListener.onItemClicked(position) }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class NewsFeedViewHolder(val itemLayoutBinding: NewsFeedItemLayoutBinding) :
        RecyclerView.ViewHolder(itemLayoutBinding.root)

    interface ItemClickListener {
        fun onItemClicked(position: Int)
    }
}