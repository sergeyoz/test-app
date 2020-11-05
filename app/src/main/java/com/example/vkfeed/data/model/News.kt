package com.example.vkfeed.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter=true)
data class News(
    @field:Json(name="attachments")
    val attachments: List<Attachment>?,
    @field:Json(name="can_doubt_category")
    val canDoubtCategory: Boolean?,
    @field:Json(name="can_set_category")
    val canSetCategory: Boolean?,
    @field:Json(name="comments")
    val comments: Comments?,
    @field:Json(name="date")
    val date: Long?,
    @field:Json(name="ext_id")
    val extId: String?,
    @field:Json(name="is_favorite")
    val isFavorite: Boolean?,
    @field:Json(name="likes")
    val likes: Likes?,
    @field:Json(name="marked_as_ads")
    val markedAsAds: Int?,
    @field:Json(name="photos")
    val photos: Photos?,
    @field:Json(name="post_id")
    val postId: Int?,
    @field:Json(name="post_source")
    val postSource: PostSource?,
    @field:Json(name="post_type")
    val postType: String?,
    @field:Json(name="push_subscription")
    val pushSubscription: PushSubscription?,
    @field:Json(name="reposts")
    val reposts: Reposts?,
    @field:Json(name="short_text_rate")
    val shortTextRate: Double?,
    @field:Json(name="source_id")
    val sourceId: Int?,
    @field:Json(name="text")
    val text: String?,
    @field:Json(name="topic_id")
    val topicId: Int?,
    @field:Json(name="track_code")
    val trackCode: String?,
    @field:Json(name="type")
    val type: String?,
    @field:Json(name="views")
    val views: Views?
)