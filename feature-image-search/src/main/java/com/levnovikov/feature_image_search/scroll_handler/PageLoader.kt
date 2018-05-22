package com.levnovikov.feature_image_search.scroll_handler

import com.levnovikov.core_api.api.error.RequestException
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.feature_image_search.ImagesAdapter

/**
 * Author: lev.novikov
 * Date: 22/5/18.
 */

class PageLoader(
        private val adapter: ImagesAdapter,
        private val imageLoader: ImageVOLoader,
        private val asyncHelper: AsyncHelper
) {

    fun clearData() {
        adapter.clearData()
    }

    fun getItemCount(): Int = adapter.itemsCount()

    fun loadNextPage(
            page: Int,
            text: String,
            onSuccess: (totalPages: Int) -> Unit,
            onError: (e: RequestException) -> Unit) {
        asyncHelper.doInBackground {
            try {
                val data = imageLoader.loadVO(page, text)
                asyncHelper.doInMainThread {
                    adapter.addItems(data.first)
                    onSuccess.invoke(data.second.pages)
                }
            } catch (e: RequestException) {
                asyncHelper.doInMainThread {
                    onError.invoke(e)
                }
            }
        }
    }
}