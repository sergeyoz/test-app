package com.example.vkfeed.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter=true)
data class Comments(
    @field:Json(name="can_post")
    val canPost: Int?,
    @field:Json(name="count")
    val count: Int?,
    @field:Json(name="groups_can_post")
    val groupsCanPost: Boolean?
)