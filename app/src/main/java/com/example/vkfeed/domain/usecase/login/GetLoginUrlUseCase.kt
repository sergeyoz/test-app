package com.example.vkfeed.domain.usecase.login

import com.example.vkfeed.domain.AppDataSource

class GetLoginUrlUseCase(
    private val appDataSource: AppDataSource
) {

    private val scope = "wall,friends"
    private val base_url = "https://oauth.vk.com/authorize"
    private val parameters = listOf(
        "client_id" to appDataSource.appId,
        "redirect_uri" to "https://oauth.vk.com/blank.html",
        "response_type" to "token",
        "display" to "mobile",
        "revoke" to 1,
        "scope" to scope,
        "v" to appDataSource.apiVersion
    )

    operator fun invoke(): String {
        return getUrl()
    }

    private fun getUrl(): String {
        return base_url + getParamsString()
    }

    private fun getParamsString(): String {
        return parameters.joinToString(
            separator = "&",
            prefix = "?"
        ) { (key, value) -> "$key=$value" }
    }

}