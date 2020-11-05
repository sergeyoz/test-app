package com.example.vkfeed.presentation.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity



inline fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}