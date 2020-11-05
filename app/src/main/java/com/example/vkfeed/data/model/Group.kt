package com.example.vkfeed.data.model


import com.example.vkfeed.domain.model.NewsOwnerDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Group(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "is_admin")
    val isAdmin: Int?,
    @field:Json(name = "is_advertiser")
    val isAdvertiser: Int?,
    @field:Json(name = "is_closed")
    val isClosed: Int?,
    @field:Json(name = "is_member")
    val isMember: Int?,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "photo_100")
    val photo100: String?,
    @field:Json(name = "photo_200")
    val photo200: String?,
    @field:Json(name = "photo_50")
    val photo50: String?,
    @field:Json(name = "screen_name")
    val screenName: String?,
    @field:Json(name = "type")
    val type: String?
)

fun Group.toDomain() = id?.let {
    NewsOwnerDomainModel(
        id = -id,
        imageSrc = photo100,
        title = name.orEmpty()
    )
}