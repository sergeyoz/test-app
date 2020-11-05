package com.example.vkfeed.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vkfeed.di.DependencyContainer
import com.example.vkfeed.di.DependencyProvider
import timber.log.Timber
import kotlin.reflect.KClass

abstract class InjectFragment : Fragment(), DependencyProvider {

    override var container: DependencyContainer? = null

    abstract fun initContainer(parentContainer: DependencyContainer?): DependencyContainer?
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        container = initContainer((activity as? DependencyProvider)?.container)
    }

    override fun onDestroyView() {
        container = null
        super.onDestroyView()
    }


}