package com.example.vkfeed.presentation.view.newsItem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.vkfeed.R
import com.example.vkfeed.databinding.LikeCounterBinding
import com.example.vkfeed.databinding.NewsItemFragmentBinding
import com.example.vkfeed.di.DependencyContainer
import com.example.vkfeed.di.provideDependency
import com.example.vkfeed.presentation.base.BindingFragment
import com.example.vkfeed.presentation.utils.visible

class NewsItemFragment : BindingFragment<NewsItemFragmentBinding>() {

    private val viewModel: NewsItemViewModel by viewModels(factoryProducer =
    {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val id = arguments?.getInt("item")
                return NewsItemViewModel(
                    provideDependency(),
                    id,
                    provideDependency(),
                    provideDependency()
                ) as T
            }
        }
    })

    private val adapter: AttachmentAdapter by lazy {
        AttachmentAdapter()
    }

    private var likeBinding: LikeCounterBinding? = null
    private val stateObserver = Observer<NewsItemViewModel.ViewState> { state ->
        likeBinding?.run {
            counter.text = state.likesCount
            icon.load(state.likeRes)
        }
        with(binding) {
            newsText.text = state.text
            errorLayout.root.visible(state.showError)
            errorLayout.errorText.text = state.errorMessage
            content.visible(state.showContent)
        }
        adapter.submitList(state.attachments)
    }

    override fun initContainer(parentContainer: DependencyContainer?): DependencyContainer? {
        return NewsItemContainer(parentContainer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initView()
    }

    private fun NewsItemFragmentBinding.initView() {
        toolbar.init()
        attachments.init()
        errorLayout.retryBtn.setOnClickListener {
            viewModel.onLikeClicked()
        }
    }

    private fun Toolbar.init() {
        val item = menu.findItem(R.id.action_like)
        likeBinding = LikeCounterBinding.bind(item.actionView)
        likeBinding!!.icon.setOnClickListener {
            viewModel.onLikeClicked()
        }
        setNavigationOnClickListener {
            viewModel.onBackBottomClick()
        }
    }

    private fun RecyclerView.init() {
        adapter = this@NewsItemFragment.adapter
        layoutManager = LinearLayoutManager(context).apply {
        }
        isNestedScrollingEnabled = false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
    }

    override fun onDestroyView() {
        likeBinding = null
        super.onDestroyView()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) = NewsItemFragmentBinding.inflate(inflater, container, attachToParent)

}



