package com.example.vkfeed.presentation.view.newsList

import com.example.vkfeed.data.repository.NewsRepositoryImpl
import com.example.vkfeed.data.source.NewsDataSource
import com.example.vkfeed.di.DependencyContainer
import com.example.vkfeed.di.get
import com.example.vkfeed.domain.repo.NewsRepository
import com.example.vkfeed.domain.usecase.news.GetNewsListUseCase
import retrofit2.Retrofit
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.starProjectedType

class NewsContainer(private val parentContainer: DependencyContainer?) :
    DependencyContainer {
    override fun <T : Any> get(type: KClass<T>): T {
        return runCatching {
            NewsContainer::class.memberProperties
                .first { it.returnType == type.starProjectedType }
                .get(this) as T
        }
            .recover { parentContainer!!.get(type) }
            .getOrThrow()
    }

    val getNewsListUseCase: GetNewsListUseCase = GetNewsListUseCase(get())

}
