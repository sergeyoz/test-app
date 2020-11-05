package com.example.vkfeed.data.model

import com.example.vkfeed.domain.model.AttachmentDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class Attachment(
    @field:Json(name = "photo")
    val photo: AttachmentPhoto?,
    @field:Json(name = "type")
    val type: String?
)

fun Attachment.toDomain() = photo?.id?.let {
    AttachmentDomainModel(
        id = photo.id,
        sizes = photo.sizes?.mapNotNull { size -> size.toDomain() } ?: emptyList()
    )
}