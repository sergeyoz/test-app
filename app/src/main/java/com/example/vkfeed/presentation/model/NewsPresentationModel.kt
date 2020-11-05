package com.example.vkfeed.presentation.model


data class NewsPresentationModel(
    val id : Int,
    val ownerImageUrl: String?,
    val ownerName : String,
    val date: String,
    val text: String
)