package com.example.vkfeed.data.repository

import com.example.vkfeed.data.model.toDomain
import com.example.vkfeed.data.source.NewsDataSource
import com.example.vkfeed.domain.AppDataSource
import com.example.vkfeed.domain.model.NewsDomainModel
import com.example.vkfeed.domain.model.NewsPageDomainModel
import com.example.vkfeed.domain.repo.NewsRepository


class NewsRepositoryImpl(
    private val source: NewsDataSource,
    private val appDataSource: AppDataSource,
) : NewsRepository {

    private val cachedNews: MutableList<NewsDomainModel> = mutableListOf()

    override suspend fun getNews(
        filters: String,
        count: Int,
        fields: String,
        startFrom: String?
    ): NewsPageDomainModel {
        val token = requireNotNull(appDataSource.accessToken)
        return source.get(
            filters = filters,
            returnBanned = 0,
            startTime = null,
            endTime = null,
            maxPhotoCount = null,
            sourceIds = null,
            count = count,
            fields = fields,
            startFrom = startFrom,
            version = appDataSource.apiVersion,
            token = token
        ).response
            .toDomain()
            .also {
                cache(startFrom == null, it.news)
            }
    }

    private fun cache(clearPrevious: Boolean, news: List<NewsDomainModel>) {
        if (clearPrevious) cachedNews.clear()
        cachedNews.addAll(news)
    }

    override suspend fun getNews(id: Int?): NewsDomainModel {
        return cachedNews.first { news -> news.id == id }
    }

    override suspend fun changeNewsLocal(news: NewsDomainModel) {
        synchronized(cachedNews) {
            val index = cachedNews.indexOfFirst { oldNews -> oldNews.id == news.id }
            cachedNews.removeAt(index)
            cachedNews.add(index, news)
        }
    }
}