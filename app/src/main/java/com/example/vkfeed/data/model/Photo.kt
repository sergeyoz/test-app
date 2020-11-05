package com.example.vkfeed.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter=true)
data class Photo(
    @field:Json(name="album_id")
    val albumId: Int?,
    @field:Json(name="date")
    val date: Int?,
    @field:Json(name="has_tags")
    val hasTags: Boolean?,
    @field:Json(name="id")
    val id: Int?,
    @field:Json(name="owner_id")
    val ownerId: Int?,
    @field:Json(name="sizes")
    val sizes: List<Size>?,
    @field:Json(name="text")
    val text: String?,
    @field:Json(name="user_id")
    val userId: Int?
)