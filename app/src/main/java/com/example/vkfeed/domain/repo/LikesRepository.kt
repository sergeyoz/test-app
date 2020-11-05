package com.example.vkfeed.domain.repo

interface LikesRepository {

    suspend fun addLike(ownerId : Int, newsId : Int) : Int?

    suspend fun deleteLike(ownerId: Int, newsId: Int) : Int?
}