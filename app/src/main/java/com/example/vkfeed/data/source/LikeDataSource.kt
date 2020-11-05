package com.example.vkfeed.data.source

import com.example.vkfeed.data.model.BaseResponse
import com.example.vkfeed.data.model.LikeResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LikeDataSource {
    @FormUrlEncoded
    @POST("likes.delete")
    suspend fun delete(
        @Field("type") type: String?,
        @Field("owner_id") ownerId: Int?,
        @Field("item_id") itemId: Int,
        @Field("v") version: String,
        @Field("access_token") token: String
    ): BaseResponse<LikeResponse>

    @FormUrlEncoded
    @POST("likes.add")
    suspend fun add(
        @Field("type") type: String?,
        @Field("owner_id") ownerId: Int?,
        @Field("item_id") itemId: Int,
        @Field("access_key") accessKey: String?,
        @Field("v") version: String,
        @Field("access_token") token: String
    ): BaseResponse<LikeResponse>
}