package com.example.vkfeed.presentation.view.login

import com.example.vkfeed.domain.usecase.login.GetLoginUrlUseCase
import com.example.vkfeed.domain.usecase.login.GetAccessTokenUseCase
import com.example.vkfeed.domain.usecase.login.SaveTokenUseCase
import com.example.vkfeed.presentation.base.BaseViewModel
import com.example.vkfeed.presentation.base.BaseViewState
import com.example.vkfeed.presentation.navigation.NavigationManager
import com.example.vkfeed.presentation.navigation.NavigationStrategy
import com.example.vkfeed.presentation.view.newsList.NewsListFragment
import java.lang.IllegalArgumentException

class LoginViewModel(
    private val navigationManager: NavigationManager,
    private val getLoginUrl: GetLoginUrlUseCase,
    private val getAccessToken: GetAccessTokenUseCase,
    private val saveToken: SaveTokenUseCase
) : BaseViewModel<LoginViewModel.LoginViewState>(LoginViewState()) {


    private val loginUrl by lazy {
        getLoginUrl()
    }

    fun showLogin() {
        state = loginState()
    }

    fun processUrl(url: String?) {
        requireNotNull(url)
        tryProcessUrl(url)
    }

    fun showError() {
        state = errorState()
    }


    private fun tryProcessUrl(url: String) {
        try {
            processAccessToken(url)
        } catch (e: IllegalArgumentException) {
        }
    }

    private fun processAccessToken(url: String) {
        val token = getAccessToken(url)
        showLoading()
        saveToken(token)
        navigateToFeed()
    }

    private fun showLoading() {
        state = loadingState()
    }

    private fun navigateToFeed() {
        navigationManager.navigate(NavigationStrategy.Replace(NewsListFragment::class.java))
    }


    data class LoginViewState(
        val loading: Boolean = false,
        val showLogin: Boolean = false,
        val showError: Boolean = false,
        val loadUrl: String? = null
    ) : BaseViewState

    private fun loadingState(): LoginViewState = LoginViewState(
        loading = true,
        showLogin = false,
        showError = false,
        loadUrl = loginUrl
    )

    private fun loginState(): LoginViewState = LoginViewState(
        loading = false,
        showLogin = true,
        showError = false,
        loadUrl = null
    )

    private fun errorState() = LoginViewState(
        loading = false,
        showLogin = false,
        showError = true,
        loadUrl = null
    )

    fun loadData() {
        state = loadingState()
    }

}