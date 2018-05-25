package com.levnovikov.system_endless_scroll

import com.levnovikov.system_async_helper.AsyncHelper


open class EndlessScrollHandler (
        private val pageLoader: PageLoader,
        private val pageLoadingListener: PageLoadingListener,
        private val positionProvider: PositionProvider,
        private val asyncHelper: AsyncHelper
) : ScrollHandler {

    companion object {
        private const val MIN_OFFSET = 20 //Make offset bigger to make smooth scrolling
    }

    internal var currentPage = 0
    internal var totalPages = 1
    internal var loadingInProgress = false //should be modified in mainThread only

    override fun reloadData() {
        resetHandlerState()
        pageLoadingListener.onStartLoading()
        loadNextPage()
    }

    internal fun resetHandlerState() {
        currentPage = 0
        totalPages = 1
        loadingInProgress = false
        pageLoader.clearData()
    }

    internal fun loadNextPage() {
        loadingInProgress = true
        pageLoader.loadNextPage(currentPage + 1,
                onSuccess = {
                    asyncHelper.doInMainThread {
                        pageLoadingListener.onLoaded()
                        currentPage += 1
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

    override fun onScroll() {
        if (!loadingInProgress &&
                currentPage < totalPages &&
                pageLoader.getItemCount() - positionProvider.getLastVisibleItemPosition() <= MIN_OFFSET) {
            loadNextPage()
        }
    }
}