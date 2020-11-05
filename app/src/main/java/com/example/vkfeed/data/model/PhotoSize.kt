package com.example.vkfeed.data.model

import com.squareup.moshi.Json

enum class PhotoSize {

    @field:Json(name = "s")
    MAX_75,
    @field:Json(name = "m")
    MAX_130,
    @field:Json(name = "o")
    MAX_130_RATIO,
    @field:Json(name = "p")
    MAX_200_RATIO,
    @field:Json(name = "q")
    MAX_320_RATIO,
    @field:Json(name = "r")
    MAX_510_RATIO,
    @field:Json(name = "x")
    MAX_640,
    @field:Json(name = "y")
    MAX_807,
    @field:Json(name = "z")
    MAX_1080,
    @field:Json(name = "w")
    MAX_2560,
    @field:Json(name = "")
    UNKNOWN
}