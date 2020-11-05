package com.example.vkfeed.presentation.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vkfeed.databinding.LoginFragmentBinding
import com.example.vkfeed.di.DependencyContainer
import com.example.vkfeed.di.provide
import com.example.vkfeed.di.provideDependency
import com.example.vkfeed.presentation.base.BindingFragment
import com.example.vkfeed.presentation.utils.visible

class LoginFragment : BindingFragment<LoginFragmentBinding>() {

    private val viewModel: LoginViewModel by viewModels(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return LoginViewModel(provideDependency(), provideDependency(), provideDependency(), provideDependency()) as T
                }
            }
        }
    )


    private val loginStateObserver = Observer<LoginViewModel.LoginViewState> {
        with(binding) {
            loginWebView.visible(it.showLogin)
            loading.root.visible(it.loading)
            it.loadUrl?.let { url -> loginWebView.loadUrl(url) }
            errorLayout.root.visible(it.showError)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            restoreState(savedInstanceState)
        } else {
            viewModel.loadData()
        }
        with(binding) {
            configWebView(loginWebView)
            errorLayout.retryBtn.setOnClickListener {
                viewModel.loadData()
            }
        }
        viewModel.stateLiveData.observe(viewLifecycleOwner, loginStateObserver)
    }

    private fun restoreState(savedInstanceState: Bundle) {
        binding.loginWebView.restoreState(savedInstanceState)
    }

    private fun configWebView(webView: WebView) {
        webView.settings.javaScriptEnabled = true
        webView.webViewClient =
            VkWebViewClient(viewModel::processUrl, viewModel::showLogin, viewModel::showError)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        saveState(outState)
        super.onSaveInstanceState(outState)
    }

    private fun saveState(outState: Bundle) {
        binding.loginWebView.saveState(outState)
    }


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) = LoginFragmentBinding.inflate(inflater, container, attachToParent)

    override fun initContainer(parentContainer: DependencyContainer?): DependencyContainer? {
        return LoginContainer(parentContainer)
    }
}

