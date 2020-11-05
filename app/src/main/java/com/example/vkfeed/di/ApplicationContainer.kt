package com.example.vkfeed.di

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.example.vkfeed.VKFeedApplication
import com.example.vkfeed.data.source.AppDataSourceImpl
import com.example.vkfeed.domain.AppDataSource
import com.example.vkfeed.presentation.navigation.NavigationManager
import com.example.vkfeed.presentation.navigation.SimpleNavigationManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.starProjectedType

class ApplicationContainer(private val context: Context) : DependencyContainer {
    val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .callTimeout(10L, TimeUnit.SECONDS)
            .connectTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS)
            .build()
    }

    val moshi: Moshi by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.vk.com/method/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    val navigationManager: NavigationManager by lazy {
        SimpleNavigationManager()
    }

    val appDataSource: AppDataSource by lazy {
        AppDataSourceImpl(context.getSharedPreferences("app", MultiDexApplication.MODE_PRIVATE))
    }

    override fun <T : Any> get(type: KClass<T>): T {
        val result =
            ApplicationContainer::class.memberProperties.first { it.returnType == type.starProjectedType }
        return result.get(this) as T
    }
}