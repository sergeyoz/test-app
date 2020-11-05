package com.example.vkfeed.presentation.view.newsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.vkfeed.databinding.NewsListFragmentBinding
import com.example.vkfeed.di.DependencyContainer
import com.example.vkfeed.di.provideDependency
import com.example.vkfeed.presentation.base.BindingFragment
import com.example.vkfeed.presentation.utils.EndlessRecyclerViewScrollListener
import com.example.vkfeed.presentation.utils.visible

class NewsListFragment : BindingFragment<NewsListFragmentBinding>() {

    private val viewModel: NewsListViewModel by viewModels(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NewsListViewModel(provideDependency(), provideDependency()) as T
            }
        }
    })
    private val adapter: NewsAdapter by lazy {
        NewsAdapter { item ->
            viewModel.onItemClick(item)
        }
    }

    private val stateObserver = Observer<NewsListViewModel.ViewState> { state ->
        with(binding){
            loading.root.visible(state.showLoading)
        }
        adapter.submitList(state.data)

    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) = NewsListFragmentBinding.inflate(inflater, container, attachToParent)

    override fun initContainer(parentContainer: DependencyContainer?): DependencyContainer? {
        return NewsContainer(parentContainer)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(binding) {
            newsList.init()
        }
        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
    }

    private fun RecyclerView.init() {
        adapter = this@NewsListFragment.adapter
        val listener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                viewModel.loadData()
            }
        }
        addOnScrollListener(listener)
    }
}