package com.example.vkfeed.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class PhotoItem(
    @field:Json(name = "access_key")
    val accessKey: String?,
    @field:Json(name = "album_id")
    val albumId: Int?,
    @field:Json(name = "can_comment")
    val canComment: Int?,
    @field:Json(name = "can_repost")
    val canRepost: Int?,
    @field:Json(name = "comments")
    val comments: Comments?,
    @field:Json(name = "date")
    val date: Int?,
    @field:Json(name = "has_tags")
    val hasTags: Boolean?,
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "likes")
    val likes: Likes?,
    @field:Json(name = "owner_id")
    val ownerId: Int?,
    @field:Json(name = "post_id")
    val postId: Int?,
    @field:Json(name = "reposts")
    val reposts: Reposts?,
    @field:Json(name = "sizes")
    val sizes: List<Size>?,
    @field:Json(name = "text")
    val text: String?,
    @field:Json(name = "user_id")
    val userId: Int?
)