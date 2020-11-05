package com.example.vkfeed.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter=true)
data class Link(
    @field:Json(name="amp")
    val amp: Amp?,
    @field:Json(name="caption")
    val caption: String?,
    @field:Json(name="description")
    val description: String?,
    @field:Json(name="is_favorite")
    val isFavorite: Boolean?,
    @field:Json(name="photo")
    val photo: Photo?,
    @field:Json(name="target")
    val target: String?,
    @field:Json(name="title")
    val title: String?,
    @field:Json(name="url")
    val url: String?
)