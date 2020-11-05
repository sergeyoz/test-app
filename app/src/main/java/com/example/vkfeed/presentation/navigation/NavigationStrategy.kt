package com.example.vkfeed.presentation.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

sealed class NavigationStrategy {

    abstract fun navigate(navigationContainerId: Int, fragmentManager: FragmentManager)

    object Back : NavigationStrategy() {
        override fun navigate(navigationContainerId: Int, fragmentManager: FragmentManager) {
            fragmentManager.popBackStack()
        }

        override fun toString(): String {
            return "Back"
        }
    }

    class Replace<T : Fragment>(
        private val fragmentClass: Class<T>,
        private val args: Bundle? = null
    ) : NavigationStrategy() {
        override fun navigate(navigationContainerId: Int, fragmentManager: FragmentManager) {
            fragmentManager.beginTransaction()
                .addToBackStack(fragmentClass.simpleName)
                .replace(navigationContainerId, fragmentClass, args)
                .commit()
        }

        override fun toString(): String {
            return "Replace(${fragmentClass.simpleName})"
        }
    }

    class Add<T : Fragment>(private val fragmentClass: Class<T>, private val args: Bundle? = null) :
        NavigationStrategy() {
        override fun navigate(navigationContainerId: Int, fragmentManager: FragmentManager) {
            fragmentManager.beginTransaction()
                .addToBackStack(fragmentClass.simpleName)
                .add(navigationContainerId, fragmentClass, args)
                .commit()
        }

        override fun toString(): String {
            return "Add(${fragmentClass.simpleName})"
        }
    }
}