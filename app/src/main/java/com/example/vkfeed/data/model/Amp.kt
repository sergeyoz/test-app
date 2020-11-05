package com.example.vkfeed.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter=true)
data class Amp(
    @field:Json(name="caption")
    val caption: String?,
    @field:Json(name="is_favorite")
    val isFavorite: Boolean?,
    @field:Json(name="title")
    val title: String?,
    @field:Json(name="url")
    val url: String?,
    @field:Json(name="views")
    val views: Int?
)