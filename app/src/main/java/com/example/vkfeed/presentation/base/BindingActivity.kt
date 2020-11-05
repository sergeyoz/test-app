package com.example.vkfeed.presentation.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import kotlin.properties.Delegates

abstract class BindingActivity<Binding : ViewBinding> : InjectActivity() {

    private var _binding: Binding by Delegates.notNull()
    protected val binding
        get() = _binding

    abstract fun inflateBinding(): Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateBinding()
        setContentView(_binding.root)
    }
}