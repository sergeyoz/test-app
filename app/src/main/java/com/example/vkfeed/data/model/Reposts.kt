package com.example.vkfeed.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter=true)
data class Reposts(
    @field:Json(name="count")
    val count: Int?,
    @field:Json(name="user_reposted")
    val userReposted: Int?
)