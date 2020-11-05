package com.example.vkfeed.domain.usecase

import com.example.vkfeed.domain.AppDataSource

class IsAuthorizedUseCase(private val appDataSource: AppDataSource) {

    operator fun invoke(): Boolean {
        return appDataSource.accessToken != null
    }
}