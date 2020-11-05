package com.example.vkfeed.domain.usecase.login

import com.example.vkfeed.domain.AppDataSource

class SaveTokenUseCase(private val appDataSource: AppDataSource) {

    operator fun invoke(token: String) {
        appDataSource.accessToken = token
    }
}