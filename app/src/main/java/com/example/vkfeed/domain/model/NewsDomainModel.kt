package com.example.vkfeed.domain.model

import java.util.*

data class NewsDomainModel(
    val id : Int,
    val text : String,
    val createdAt : Date,
    val owner : NewsOwnerDomainModel,
    val attachments: List<AttachmentDomainModel>,
    val likes: LikesDomainModel
)