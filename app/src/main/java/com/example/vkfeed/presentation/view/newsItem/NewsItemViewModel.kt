package com.example.vkfeed.presentation.view.newsItem

import androidx.lifecycle.viewModelScope
import com.example.vkfeed.R
import com.example.vkfeed.domain.model.AttachmentDomainModel
import com.example.vkfeed.domain.model.NewsDomainModel
import com.example.vkfeed.domain.usecase.news.GetNewsUseCase
import com.example.vkfeed.domain.usecase.news.SetLikeUseCase
import com.example.vkfeed.presentation.base.BaseViewModel
import com.example.vkfeed.presentation.base.BaseViewState
import com.example.vkfeed.presentation.navigation.NavigationManager
import com.example.vkfeed.presentation.navigation.NavigationStrategy
import kotlinx.coroutines.launch
import timber.log.Timber

class NewsItemViewModel(
    private val navigationManager: NavigationManager,
    private val newsId: Int?,
    private val getNewsUseCase: GetNewsUseCase,
    private val setLike: SetLikeUseCase
) : BaseViewModel<NewsItemViewModel.ViewState>(ViewState()) {

    data class ViewState(
        val likesCount: String = "",
        val likeRes: Int = R.drawable.like,
        val text: String = "",
        val attachments: List<AttachmentDomainModel> = emptyList(),
        val showError: Boolean = false,
        val errorMessage: String = "",
        val showContent: Boolean = false
    ) : BaseViewState {

    }

    init {
        loadNews()
    }

    private var news: NewsDomainModel? = null
    private fun loadNews() {
        viewModelScope.launch {
            val result = getNewsUseCase(newsId)
            processResult(result)
        }
    }

    private fun processResult(result: GetNewsUseCase.Result) {
        when (result) {
            is GetNewsUseCase.Result.Success -> reduceNewsState(result.data)
            is GetNewsUseCase.Result.Error -> reduceError(result.error, "Новость не найдена")
        }
    }

    private fun reduceError(error: Throwable, message: String) {
        Timber.e(error)
        state = state.copy(
            showContent = false, showError = true,
            errorMessage = message
        )
    }

    private fun reduceNewsState(news: NewsDomainModel) {
        this.news = news
        state = state.copy(
            showContent = true,
            showError = false,
            likesCount = news.likes.count.toString(),
            text = news.text,
            likeRes = if (news.likes.userLiked) R.drawable.like else R.drawable.like_outline,
            attachments = news.attachments
        )
    }

    fun onLikeClicked() {
        news?.let { news ->
            performSetLike(news)
        }
    }

    private fun performSetLike(news: NewsDomainModel) {
        viewModelScope.launch {
            when (val result = setLike(news)) {
                is SetLikeUseCase.Result.Success ->
                    reduceNewsState(result.news)
                is SetLikeUseCase.Result.Error ->
                    reduceError(
                        result.error,
                        "Не удалось поставить/убрать лайк. Попробуйте еще раз."
                    )
            }
        }
    }

    fun onBackBottomClick() {
        navigationManager.navigate(NavigationStrategy.Back)
    }
}