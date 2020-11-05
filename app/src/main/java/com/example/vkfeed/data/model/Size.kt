package com.example.vkfeed.data.model


import com.example.vkfeed.domain.model.ImageSizeDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Size(
    @field:Json(name = "height")
    val height: Int?,
    @field:Json(name = "type")
    val size: PhotoSize?,
    @field:Json(name = "url")
    val url: String?,
    @field:Json(name = "width")
    val width: Int?
)

fun Size.toDomain() = url?.let {
    ImageSizeDomainModel(url, size ?: PhotoSize.UNKNOWN)
}