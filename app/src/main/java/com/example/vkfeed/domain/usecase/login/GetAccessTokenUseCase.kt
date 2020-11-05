package com.example.vkfeed.domain.usecase.login

import timber.log.Timber

class GetAccessTokenUseCase {

    private val regex = "access_token=(.*?)&".toRegex()

    operator fun invoke(url: String): String {
        val accessToken = regex.find(url)
        requireNotNull(accessToken)
        return accessToken.groupValues[1].also {
            Timber.d("access token = $it")
        }
    }
}