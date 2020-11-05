package com.example.vkfeed.data.model

import com.example.vkfeed.domain.model.LikesDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Likes(
    @field:Json(name = "can_like")
    val canLike: Int?,
    @field:Json(name = "can_publish")
    val canPublish: Int?,
    @field:Json(name = "count")
    val count: Int?,
    @field:Json(name = "user_likes")
    val userLikes: Int?
)

fun Likes.toDomain() = LikesDomainModel(
    count = count ?: 0,
    userLiked = userLikes == 1
)
