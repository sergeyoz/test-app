package com.example.vkfeed.di

import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.starProjectedType

interface DependencyContainer {
    fun <T : Any> get(type: KClass<T>): T
}

inline fun <reified T : Any> DependencyContainer?.get(): T {
    requireNotNull(this) {
        "Container not initialized"
    }
    return get(T::class)
}
