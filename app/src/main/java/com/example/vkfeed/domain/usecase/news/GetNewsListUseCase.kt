package com.example.vkfeed.domain.usecase.news

import com.example.vkfeed.domain.model.NewsDomainModel
import com.example.vkfeed.domain.model.NewsPageDomainModel
import com.example.vkfeed.domain.repo.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetNewsListUseCase(private val repository: NewsRepository) {

    sealed class Result {
        data class Success(val data: List<NewsDomainModel>) : Result()
        data class Error(val e: Throwable) : Result()
    }

    private var nextPage: String? = null
    private var currentPage: String? = null

    sealed class LoadAction {

        protected var nextPage: String? = null
        protected var currentPage: String? = null
        fun init(last: String?, next: String?) {
            currentPage = last
            nextPage = next
        }

        abstract suspend fun load(
            get: suspend (String?) -> NewsPageDomainModel
        ): NewsPageDomainModel

        object Next : LoadAction() {
            override suspend fun load(
                get: suspend (String?) -> NewsPageDomainModel
            ): NewsPageDomainModel {
                return get(nextPage)
            }
        }

        object Retry : LoadAction() {
            override suspend fun load(
                get: suspend (String?) -> NewsPageDomainModel
            ): NewsPageDomainModel {
                return get(currentPage)
            }
        }
    }


    suspend operator fun invoke(actionFlow: Flow<LoadAction>): Flow<Result> {
        return actionFlow.map { action ->
            action.init(currentPage, nextPage)
            loadPage(action)
                .savePage()
                .news
                .filterNewsWithoutText()
                .toResult()
        }
            .flowOn(Dispatchers.IO)
            .catch { e ->
                emit(Result.Error(e))
            }

    }

    private fun List<NewsDomainModel>.toResult() =
        if (isEmpty()) Result.Error(RuntimeException("no data")) else Result.Success(this)

    private fun List<NewsDomainModel>.filterNewsWithoutText() =
        filter { it.text.isNotEmpty() }

    private fun NewsPageDomainModel.savePage() =
        also { changeStartFrom(it.nextPage) }

    private fun changeStartFrom(nextFrom: String?) {
        currentPage = nextPage
        nextPage = nextFrom
    }


    private suspend fun loadPage(action: LoadAction): NewsPageDomainModel {
        return action.load { page ->
            repository.getNews(
                filters = "post",
                count = 20,
                fields = "photo_100",
                startFrom = page
            )
        }
    }


}





