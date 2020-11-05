package com.example.vkfeed.data.model


import com.example.vkfeed.domain.model.NewsOwnerDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Profile(
    @field:Json(name = "can_access_closed")
    val canAccessClosed: Boolean?,
    @field:Json(name = "first_name")
    val firstName: String?,
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "is_closed")
    val isClosed: Boolean?,
    @field:Json(name = "is_service")
    val isService: Boolean?,
    @field:Json(name = "last_name")
    val lastName: String?,
    @field:Json(name = "online")
    val online: Int?,
    @field:Json(name = "online_info")
    val onlineInfo: OnlineInfo?,
    @field:Json(name = "photo_100")
    val photo100: String?,
    @field:Json(name = "photo_50")
    val photo50: String?,
    @field:Json(name = "screen_name")
    val screenName: String?,
    @field:Json(name = "sex")
    val sex: Int?
)

fun Profile.toDomain() = id?.let {
    NewsOwnerDomainModel(
        id = id,
        imageSrc = photo100,
        title = "${firstName.orEmpty()} ${lastName.orEmpty()}"
    )
}
