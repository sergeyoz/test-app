package com.example.vkfeed.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter=true)
data class OnlineInfo(
    @field:Json(name="is_mobile")
    val isMobile: Boolean?,
    @field:Json(name="is_online")
    val isOnline: Boolean?,
    @field:Json(name="visible")
    val visible: Boolean?
)