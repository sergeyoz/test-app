package com.example.vkfeed.presentation.view.newsList

import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.vkfeed.domain.model.NewsDomainModel
import com.example.vkfeed.domain.usecase.news.GetNewsListUseCase
import com.example.vkfeed.presentation.base.BaseViewModel
import com.example.vkfeed.presentation.base.BaseViewState
import com.example.vkfeed.presentation.model.NewsPresentationModel
import com.example.vkfeed.presentation.navigation.NavigationManager
import com.example.vkfeed.presentation.navigation.NavigationStrategy
import com.example.vkfeed.presentation.view.newsItem.NewsItemFragment
import com.example.vkfeed.utils.format
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class NewsListViewModel(
    private val navigationManager: NavigationManager,
    private val getNewsListUseCase: GetNewsListUseCase
) :
    BaseViewModel<NewsListViewModel.ViewState>(ViewState()) {

    data class ViewState(
        val showLoading: Boolean = false,
        val data: List<NewsPresentationModel> = emptyList()
    ) : BaseViewState

    private val loadAction: MutableLiveData<GetNewsListUseCase.LoadAction> =
        MutableLiveData(GetNewsListUseCase.LoadAction.Next)

    init {
        state = ViewState(showLoading = true)
        startCollectNews()
    }

    fun loadData() {
        loadAction.value = GetNewsListUseCase.LoadAction.Next
    }

    private fun startCollectNews() {
        viewModelScope.launch {
            getNewsListUseCase.invoke(loadAction.asFlow())
                .collectLatest { result ->
                    when (result) {
                        is GetNewsListUseCase.Result.Success ->
                            reduceSuccess(result.data)
                        is GetNewsListUseCase.Result.Error ->
                            showError(result.e)
                    }
                }
        }
    }

    private fun reduceSuccess(data: List<NewsDomainModel>) {
        val result = mapNews(data)
        state = ViewState(data = state.data + result)
    }

    private fun mapNews(data: List<NewsDomainModel>): List<NewsPresentationModel> {
        return data.map {
            NewsPresentationModel(
                id = it.id,
                ownerName = it.owner.title,
                ownerImageUrl = it.owner.imageSrc,
                date = it.createdAt.format("dd.MM.yyyy HH:mm"),
                text = it.text
            )
        }
    }

    private fun showError(e: Throwable) {
        Timber.e(e)
    }

    fun onItemClick(item: NewsPresentationModel) {
        navigationManager.navigate(
            NavigationStrategy.Replace(
                NewsItemFragment::class.java,
                bundleOf("item" to item.id)
            )
        )
    }

}