package com.example.vkfeed.data.model

import com.squareup.moshi.Json

data class LikeResponse(
    @field:Json(name = "likes")
    val likes: Int?
)
