package com.example.vkfeed.presentation.base

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.vkfeed.di.DependencyContainer
import com.example.vkfeed.di.DependencyProvider

abstract class InjectActivity : FragmentActivity(), DependencyProvider {

    override var container: DependencyContainer? = null

    abstract fun initContainer(parentContainer: DependencyContainer?): DependencyContainer?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parentContainer = (application as DependencyProvider).container
        container = initContainer(parentContainer)
    }

    override fun onDestroy() {
        container = null
        super.onDestroy()
    }

}