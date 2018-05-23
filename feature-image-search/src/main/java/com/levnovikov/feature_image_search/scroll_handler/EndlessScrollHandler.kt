package com.levnovikov.feature_image_search.scroll_handler

import android.annotation.SuppressLint
import android.support.annotation.MainThread
import android.support.annotation.VisibleForTesting
import com.levnovikov.core_common.AsyncHelper

interface PageLoadingListener {
    fun onStartLoading()
    fun onLoaded()
    fun onError(e: Exception)
}

class EndlessScrollHandler (
        private val pageLoader: PageLoader,
        private val pageLoadingListener: PageLoadingListener,
        private val positionProvider: PositionProvider,
        private val asyncHelper: AsyncHelper
) : ScrollHandler {

    companion object {
        private const val MIN_OFFSET = 20 //Make offset bigger to make smooth scrolling
    }

    private var currentPage = 0
    private var totalPages = 1
    private var loadingInProgress = false //should be modified in mainThread only

    private var text: String = ""

    @SuppressLint("VisibleForTests")
    @MainThread
    override fun reloadData(text: String) {
        if (text.isEmpty()) return
        this.text = text
        resetHandlerState()
        pageLoadingListener.onStartLoading()
        loadNextPage()
    }

    private fun resetHandlerState() {
        currentPage = 0
        totalPages = 1
        loadingInProgress = false
        pageLoader.clearData()
    }

    @MainThread
    @VisibleForTesting
    internal fun loadNextPage() {
        loadingInProgress = true
        pageLoader.loadNextPage(++currentPage, text,
                onSuccess = {
                    asyncHelper.doInMainThread {
                        pageLoadingListener.onLoaded()
                        totalPages = it
                        loadingInProgress = false
                        onScroll() //check conditions and load more data if needed
                    }
                },
                onError = {
                    asyncHelper.doInMainThread {
                        resetHandlerState()
                        pageLoadingListener.onError(it)
                    }
                })
    }

    @SuppressLint("VisibleForTests")
    override fun onScroll() {
        if (!loadingInProgress &&
                currentPage < totalPages &&
                pageLoader.getItemCount() - positionProvider.getLastVisibleItemPosition() <= MIN_OFFSET) {
            loadNextPage()
        }
    }
}

interface PositionProvider {
    fun getLastVisibleItemPosition(): Int
}