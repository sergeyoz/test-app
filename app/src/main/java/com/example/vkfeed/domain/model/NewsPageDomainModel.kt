package com.example.vkfeed.domain.model

data class NewsPageDomainModel(
    val news: List<NewsDomainModel>,
    val nextPage: String?
)