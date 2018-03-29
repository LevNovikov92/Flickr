package com.levnovikov.feature_image_search.ui.scroll_handler

import android.annotation.SuppressLint
import android.support.annotation.MainThread
import android.support.annotation.VisibleForTesting
import android.util.Log
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.feature_image_search.data.entities.PagerData
import com.levnovikov.feature_image_search.ui.ImageSearchView
import com.levnovikov.feature_image_search.ui.ImageVO
import com.levnovikov.feature_image_search.ui.ImagesAdapter

interface ImageVOLoader {
    fun loadVO(page: Int, text: String): Pair<List<ImageVO>, PagerData>
}

interface PageLoadingListener {
    fun onStartLoading()
    fun onLoaded()
}

class EndlessScrollHandler (
        private val adapter: ImagesAdapter,
        private val imageLoader: ImageVOLoader,
        private val pageLoadingListener: PageLoadingListener,
        private val asyncHelper: AsyncHelper,
        val view: ImageSearchView
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
        adapter.clearData()
    }

    @MainThread
    @VisibleForTesting
    internal fun loadNextPage() {
        loadingInProgress = true
        asyncHelper.doInBackground {
            val data = imageLoader.loadVO(++currentPage, text)
            Log.d(">>>> LoadNewData","(loading finished): currentPage = ${currentPage - 1}")
            asyncHelper.doInMainThread {
                adapter.addItems(data.first)
                totalPages = data.second.pages
                loadingInProgress = false
                onScroll() //check conditions and load more data if needed
            }
        }
        pageLoadingListener.onLoaded()
    }

    @SuppressLint("VisibleForTests")
    override fun onScroll() {
        if (!loadingInProgress &&
                currentPage < totalPages &&
                adapter.itemsCount() - view.getLastVisibleItemPosition() <= MIN_OFFSET) {
            loadNextPage()
        }
    }

    override fun getAdapter() = adapter
}