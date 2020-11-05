package com.example.vkfeed.domain.repo

import com.example.vkfeed.domain.model.NewsDomainModel
import com.example.vkfeed.domain.model.NewsPageDomainModel

interface NewsRepository {
    suspend fun getNews(filters: String, count: Int, fields: String, startFrom : String?) : NewsPageDomainModel

    suspend fun getNews(id: Int?) : NewsDomainModel
    suspend fun changeNewsLocal(news: NewsDomainModel)

}