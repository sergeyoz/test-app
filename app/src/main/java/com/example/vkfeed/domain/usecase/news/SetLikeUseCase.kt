package com.example.vkfeed.domain.usecase.news

import com.example.vkfeed.domain.model.NewsDomainModel
import com.example.vkfeed.domain.repo.LikesRepository
import com.example.vkfeed.domain.repo.NewsRepository

class SetLikeUseCase(private val likeRepository: LikesRepository, private val newsRepository: NewsRepository) {

    sealed class Result {
        class Success(val news: NewsDomainModel) : Result()
        class Error(val error: Throwable) : Result()
    }

    suspend operator fun invoke(news: NewsDomainModel): Result {
        return runCatching {
            val newLikesCount = changeLikeState(news)
            requireNotNull(newLikesCount) {
                "likes data is null"
            }
            createNews(news, newLikesCount).also { changeCache(it) }
        }.fold(onSuccess = Result::Success, onFailure = Result::Error)
    }

    private suspend fun changeCache(it: NewsDomainModel) {
        newsRepository.changeNewsLocal(it)
    }

    private fun createNews(news: NewsDomainModel, newLikesCount: Int): NewsDomainModel {
        val likes = news.likes
        return news.copy(likes = likes.copy(count = newLikesCount, userLiked = !likes.userLiked))
    }

    private suspend fun changeLikeState(news: NewsDomainModel): Int? {
        return if (news.likes.userLiked)
            likeRepository.deleteLike(news.owner.id, news.id)
        else
            likeRepository.addLike(news.owner.id, news.id)
    }

}
