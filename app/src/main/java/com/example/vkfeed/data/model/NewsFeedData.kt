package com.example.vkfeed.data.model


import com.example.vkfeed.domain.model.NewsDomainModel
import com.example.vkfeed.domain.model.NewsOwnerDomainModel
import com.example.vkfeed.domain.model.NewsPageDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class NewsFeedData(
    @field:Json(name = "groups")
    val groups: List<Group>?,
    @field:Json(name = "items")
    val items: List<News>?,
    @field:Json(name = "next_from")
    val nextFrom: String?,
    @field:Json(name = "profiles")
    val profiles: List<Profile>?
)

fun NewsFeedData.toDomain() = NewsPageDomainModel(
    news = getNewsDomain(),
    nextPage = nextFrom
)

private fun NewsFeedData.getNewsDomain(): List<NewsDomainModel> {
    return items?.mapNotNull { news ->
        val postId = news.postId
        val text = news.text.orEmpty()
        val createdAt = news.mapDate()
        val owner = getOwner(news)
        val attachments = news.domainAttachment()
        val likes = news.likes?.toDomain()
        if (postId == null || createdAt == null || owner == null || likes == null) null
        else
            NewsDomainModel(
                id = postId,
                text = text,
                createdAt = createdAt,
                owner = owner,
                attachments = attachments ?: emptyList(),
                likes = likes
            )
    } ?: emptyList()
}

private fun News.domainAttachment() =
    attachments?.mapNotNull { attachment -> attachment.toDomain() }

private fun NewsFeedData.getOwner(news: News) = news.sourceId?.let { ownerId -> mapOwner(ownerId) }

private fun News.mapDate() = date?.let { time -> Date(time * 1000) }

private fun NewsFeedData.mapOwner(ownerId: Int): NewsOwnerDomainModel? {
    return if (ownerId > 0) {
        profiles?.firstOrNull { profile -> profile.id == ownerId }?.toDomain()
    } else {
        groups?.firstOrNull { profile -> profile.id == -ownerId }?.toDomain()
    }
}
