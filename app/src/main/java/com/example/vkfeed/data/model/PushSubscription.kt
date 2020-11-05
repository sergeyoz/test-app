package com.example.vkfeed.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter=true)
data class PushSubscription(
    @field:Json(name="is_subscribed")
    val isSubscribed: Boolean?
)