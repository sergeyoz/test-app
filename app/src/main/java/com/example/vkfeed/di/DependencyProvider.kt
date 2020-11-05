package com.example.vkfeed.di

import kotlin.reflect.KClass

interface DependencyProvider {
    var container: DependencyContainer?
    fun <T : Any> provideDependency(type: KClass<T>): T {
        val nonNulContainer = requireNotNull(container) {
            "Container must be initialized"
        }
        return nonNulContainer.get(type)
    }
}

inline fun <reified T : Any> DependencyProvider.provideDependency(): T =
    provideDependency(T::class)

inline fun <reified T : Any> DependencyProvider.provide(): Lazy<T> =
    lazy { provideDependency(T::class) }