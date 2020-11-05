package com.example.vkfeed.data.source

import com.example.vkfeed.data.model.BaseResponse
import com.example.vkfeed.data.model.NewsFeedData
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NewsDataSource {
    @FormUrlEncoded
    @POST("newsfeed.get")
    suspend fun get(
        @Field("filters") filters: String?,
        @Field("return_banned") returnBanned: Int?,
        @Field("start_time") startTime: Long?,
        @Field("end_time") endTime: Long?,
        @Field("max_photos") maxPhotoCount: Int?,
        @Field("source_ids") sourceIds: String?,
        @Field("start_from") startFrom: String?,
        @Field("count") count: Int?,
        @Field("fields") fields: String?,
        @Field("v") version: String,
        @Field("access_token") token: String
    ): BaseResponse<NewsFeedData>
}