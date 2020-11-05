package com.example.vkfeed.presentation.navigation

import androidx.fragment.app.FragmentManager
import timber.log.Timber

class SimpleNavigationManager() : NavigationManager {

    private var containerId: Int? = null
    private var fragmentManager: FragmentManager? = null

    override fun route(containerId: Int, manager: FragmentManager) {
        this.containerId = containerId
        fragmentManager = manager
    }

    override fun navigate(strategy: NavigationStrategy) {
        if (containerId != null && fragmentManager != null) {
            Timber.d("navigate = $strategy")
            strategy.navigate(containerId!!, fragmentManager!!)
        }
    }

    override fun unRoute() {
        containerId = null
        fragmentManager = null
    }
}