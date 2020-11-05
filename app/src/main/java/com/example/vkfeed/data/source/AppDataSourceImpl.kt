package com.example.vkfeed.data.source

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.vkfeed.BuildConfig
import com.example.vkfeed.domain.AppDataSource


class AppDataSourceImpl(private val preferences: SharedPreferences) : AppDataSource {
    companion object {
        private const val API_TOKEN_KEY = "api_token"
    }

    override val appId = BuildConfig.API_APP_ID
    override val clientSecret = BuildConfig.CLIENT_SECRET
    override val apiVersion = BuildConfig.API_VERSION
    override var accessToken: String?
        get() = preferences.getString(API_TOKEN_KEY, null)
        set(value) {
            if (value == null)
                preferences.edit { remove(API_TOKEN_KEY) }
            else
                preferences.edit { putString(API_TOKEN_KEY, value) }
        }
}