package com.example.vkfeed.presentation.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import timber.log.Timber


abstract class EndlessRecyclerViewScrollListener(
    private val mLayoutManager: RecyclerView.LayoutManager?,
    private var visibleThreshold: Int = 5
) : RecyclerView.OnScrollListener() {
    private var currentPage = 1
    private var previousTotalItemCount = 0
    private var loading = true
    private val startingPageIndex = 1

    init {
        if (mLayoutManager is StaggeredGridLayoutManager) {
            visibleThreshold *= mLayoutManager.spanCount
        } else if (mLayoutManager is GridLayoutManager) {
            visibleThreshold *= mLayoutManager.spanCount
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        val sz = lastVisibleItemPositions.size
        for (i in 0 until sz) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        if(mLayoutManager == null) return

        val totalItemCount = mLayoutManager.itemCount

        if (totalItemCount < visibleThreshold) return

        when (mLayoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = mLayoutManager.findLastVisibleItemPositions(null)
                // get maximum element within the list
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition()
            is LinearLayoutManager -> lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition()
        }

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount)
            loading = true
        }
    }

    fun resetState() {
        this.currentPage = this.startingPageIndex
        this.previousTotalItemCount = 0
        this.loading = true
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int)

}