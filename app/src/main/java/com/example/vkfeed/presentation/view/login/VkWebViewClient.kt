package com.example.vkfeed.presentation.view.login

import android.graphics.Bitmap
import android.os.Build
import android.webkit.*
import timber.log.Timber

class VkWebViewClient(
    private val urlHandler: (String?) -> Unit,
    private val successLoad: () -> Unit,
    private val errorHandler: () -> Unit
) : WebViewClient() {

    private var loadWithError: Boolean = false

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        if (!loadWithError) {
            successLoad()
        }
    }

    override fun onReceivedError(
        view: WebView?,
        errorCode: Int,
        description: String?,
        failingUrl: String?
    ) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            handleErrorMessage(description)
        }
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            handleErrorMessage(error?.description?.toString())
        }
    }

    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            handleErrorMessage(errorResponse?.reasonPhrase)
        }
    }

    private fun handleErrorMessage(message: String?) {
        if (!message.isNullOrBlank()) {
            Timber.d("error = $message")
            loadWithError = true
            errorHandler()
        }
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        loadWithError = false
        urlHandler(url)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return false
    }
}