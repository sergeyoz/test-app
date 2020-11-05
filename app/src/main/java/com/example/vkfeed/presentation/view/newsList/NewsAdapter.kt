package com.example.vkfeed.presentation.view.newsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.vkfeed.databinding.NewsItemBinding
import com.example.vkfeed.presentation.model.NewsPresentationModel

class NewsAdapter(private val onClickAction: (NewsPresentationModel) -> Unit) :
    ListAdapter<NewsPresentationModel, NewsAdapter.FeedViewHolder>(DIFF_CALLBACK) {

    fun addItems(items: List<NewsPresentationModel>) {
        val newList = currentList + items
        submitList(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder.of(parent, viewType, onClickAction)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class FeedViewHolder private constructor(
        private val binding: NewsItemBinding,
        private val onClickAction: (NewsPresentationModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsPresentationModel) {
            with(binding) {
                ownerImage.load(item.ownerImageUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                newsText.text = item.text
                newsDate.text = item.date
                owner.text = item.ownerName
                root.setOnClickListener {
                    onClickAction(item)
                }
            }
        }

        companion object {
            fun of(
                parent: ViewGroup,
                type: Int,
                onClickAction: (NewsPresentationModel) -> Unit
            ): FeedViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = NewsItemBinding.inflate(inflater, parent, false)
                return FeedViewHolder(binding, onClickAction)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsPresentationModel>() {
            override fun areItemsTheSame(
                oldItem: NewsPresentationModel,
                newItem: NewsPresentationModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: NewsPresentationModel,
                newItem: NewsPresentationModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}