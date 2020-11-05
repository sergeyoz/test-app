package com.example.vkfeed.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    @field:Json(name = "response")
    val response: T
)