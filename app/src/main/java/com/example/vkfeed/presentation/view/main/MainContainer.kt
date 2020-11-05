package com.example.vkfeed.presentation.view.main

import com.example.vkfeed.data.repository.LikesRepositoryImpl
import com.example.vkfeed.data.repository.NewsRepositoryImpl
import com.example.vkfeed.data.source.LikeDataSource
import com.example.vkfeed.data.source.NewsDataSource
import com.example.vkfeed.di.DependencyContainer
import com.example.vkfeed.di.get
import com.example.vkfeed.domain.repo.LikesRepository
import com.example.vkfeed.domain.repo.NewsRepository
import com.example.vkfeed.domain.usecase.IsAuthorizedUseCase
import com.example.vkfeed.presentation.view.newsItem.NewsItemContainer
import retrofit2.Retrofit
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.starProjectedType

class MainContainer(private val parent: DependencyContainer?) : DependencyContainer {
    override fun <T : Any> get(type: KClass<T>): T {
        return runCatching {
            MainContainer::class.memberProperties
                .first { it.returnType == type.starProjectedType }
                .get(this) as T
        }
            .recover { parent!!.get(type) }
            .getOrThrow()
    }

    val newsDataSource: NewsDataSource =
        get<Retrofit>().create(NewsDataSource::class.java)

    val likeDataSource: LikeDataSource =
        get<Retrofit>().create(LikeDataSource::class.java)

    val newsRepository: NewsRepository = NewsRepositoryImpl(get(), get())

    val likesRepository: LikesRepository = LikesRepositoryImpl(get(), get())

    val isAuthorizedUseCase = IsAuthorizedUseCase(get())
}