package com.example.vkfeed.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vkfeed.presentation.view.login.LoginViewModel
import timber.log.Timber
import kotlin.properties.Delegates

abstract class BaseViewModel<State : BaseViewState>(initialState : State) : ViewModel() {

    protected var state: State by Delegates.observable(initialState) { property, oldValue, newValue ->
        if (oldValue != newValue) {
            Timber.d("Change state. old = $oldValue\n new = $newValue")
            _stateLiveData.value = newValue
        }
    }

    private val _stateLiveData = MutableLiveData<State>()
    val stateLiveData: LiveData<State>
        get() = _stateLiveData

}