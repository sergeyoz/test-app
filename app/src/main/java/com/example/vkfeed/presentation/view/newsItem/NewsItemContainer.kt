package com.example.vkfeed.presentation.view.newsItem

import com.example.vkfeed.di.DependencyContainer
import com.example.vkfeed.di.get
import com.example.vkfeed.domain.usecase.news.GetNewsUseCase
import com.example.vkfeed.domain.usecase.news.SetLikeUseCase
import com.example.vkfeed.presentation.view.newsList.NewsContainer
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.starProjectedType

class NewsItemContainer(private val parent: DependencyContainer?) : DependencyContainer {
    override fun <T : Any> get(type: KClass<T>): T {
        return runCatching {
            NewsItemContainer::class.memberProperties
                .first { it.returnType == type.starProjectedType }
                .get(this) as T
        }
            .recover { parent!!.get(type) }
            .getOrThrow()
    }

    val getNewsUseCase: GetNewsUseCase = GetNewsUseCase(get())
    val setLikeUseCase: SetLikeUseCase = SetLikeUseCase(get(), get())
}