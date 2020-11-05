package com.example.vkfeed.data.repository

import com.example.vkfeed.data.source.LikeDataSource
import com.example.vkfeed.domain.AppDataSource
import com.example.vkfeed.domain.repo.LikesRepository

class LikesRepositoryImpl(
    private val likeDataSource: LikeDataSource,
    private val appDataSource: AppDataSource
) : LikesRepository {
    override suspend fun addLike(ownerId: Int, newsId: Int): Int? {
        val token = requireNotNull(appDataSource.accessToken)
        return likeDataSource.add(
            "post",
            ownerId,
            newsId,
            null,
            appDataSource.apiVersion,
            token
        )
            .response
            .likes
    }

    override suspend fun deleteLike(ownerId: Int, newsId: Int): Int? {
        val token = requireNotNull(appDataSource.accessToken)
        return likeDataSource.delete(
            "post",
            ownerId,
            newsId,
            appDataSource.apiVersion,
            token
        )
            .response
            .likes
    }
}