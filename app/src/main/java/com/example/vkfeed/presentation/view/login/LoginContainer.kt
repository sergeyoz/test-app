package com.example.vkfeed.presentation.view.login

import com.example.vkfeed.di.ApplicationContainer
import com.example.vkfeed.di.DependencyContainer
import com.example.vkfeed.di.get
import com.example.vkfeed.domain.usecase.login.GetAccessTokenUseCase
import com.example.vkfeed.domain.usecase.login.GetLoginUrlUseCase
import com.example.vkfeed.domain.usecase.login.SaveTokenUseCase
import com.example.vkfeed.presentation.view.newsList.NewsContainer
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.starProjectedType

class LoginContainer(private val parentContainer: DependencyContainer?) : DependencyContainer {
    override fun <T : Any> get(type: KClass<T>): T {
        return runCatching {
            LoginContainer::class.memberProperties.first { it.returnType == type.starProjectedType }.get(this) as T
        }
            .recover { parentContainer!!.get(type) }
            .getOrThrow()
    }

    val getLoginUseCase: GetLoginUrlUseCase = GetLoginUrlUseCase(get())

    val getAccessTokenUseCase: GetAccessTokenUseCase = GetAccessTokenUseCase()

    val saveTokenUseCase: SaveTokenUseCase = SaveTokenUseCase(get())


}