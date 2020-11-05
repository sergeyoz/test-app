package com.example.vkfeed.utils

import java.text.SimpleDateFormat
import java.util.*


fun Date.format(format: String): String {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(this)
}