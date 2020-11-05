package com.example.vkfeed.domain.model

import com.example.vkfeed.data.model.PhotoSize

data class ImageSizeDomainModel(
    val imageUrl : String,
    val type : PhotoSize
)
