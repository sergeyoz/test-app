package com.example.vkfeed.domain.usecase.news

import com.example.vkfeed.domain.model.NewsDomainModel
import com.example.vkfeed.domain.repo.NewsRepository

class GetNewsUseCase(private val newsRepository: NewsRepository) {

    sealed class Result {
        class Success(val data: NewsDomainModel) : Result()
        class Error(val error: Throwable) : Result()
    }

    suspend operator fun invoke(newsId: Int?): Result {
        return runCatching {
            newsRepository.getNews(newsId)
        }
            .fold(onSuccess = Result::Success, onFailure = Result::Error)
    }
}