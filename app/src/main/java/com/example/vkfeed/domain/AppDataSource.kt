package com.example.vkfeed.domain

interface AppDataSource {
    val appId: Int
    val clientSecret: String
    val apiVersion: String
    var accessToken : String?
}