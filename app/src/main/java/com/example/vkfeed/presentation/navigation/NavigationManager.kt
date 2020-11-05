package com.example.vkfeed.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface NavigationManager {
    fun route(containerId: Int, manager: FragmentManager)
    fun navigate(strategy: NavigationStrategy)
    fun unRoute()
}