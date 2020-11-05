package com.example.vkfeed.presentation.view.newsItem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.vkfeed.data.model.PhotoSize
import com.example.vkfeed.databinding.ImageItemBinding
import com.example.vkfeed.domain.model.AttachmentDomainModel
import timber.log.Timber

class AttachmentAdapter() :
    ListAdapter<AttachmentDomainModel, AttachmentAdapter.AttachmentViewHolder>(
        DIFF_CALLBACK
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttachmentViewHolder {
        return AttachmentViewHolder.of(parent, viewType)
    }


    override fun onBindViewHolder(holder: AttachmentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AttachmentViewHolder(private val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {

            fun of(parent: ViewGroup, viewType: Int): AttachmentViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ImageItemBinding.inflate(inflater, parent, false)
                return AttachmentViewHolder(binding)
            }
        }

        fun bind(item: AttachmentDomainModel) {
            val size = item.sizes.maxByOrNull { it.type }
            binding.image.load(size?.imageUrl){
                crossfade(true)
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AttachmentDomainModel>() {
            override fun areItemsTheSame(
                oldItem: AttachmentDomainModel,
                newItem: AttachmentDomainModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: AttachmentDomainModel,
                newItem: AttachmentDomainModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}