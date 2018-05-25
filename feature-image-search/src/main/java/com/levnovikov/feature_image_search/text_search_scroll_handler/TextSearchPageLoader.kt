package com.levnovikov.feature_image_search.text_search_scroll_handler

import com.levnovikov.core_api.api.error.RequestException
import com.levnovikov.system_async_helper.AsyncHelper
import com.levnovikov.feature_image_search.ImagesAdapter
import com.levnovikov.system_endless_scroll.PageLoader

interface TextSearchPageLoader : PageLoader {
    fun updateSearchText(text: String)
}

class TextSearchPageLoaderImpl(
        private val adapter: ImagesAdapter,
        private val imageLoader: ImageVOLoader,
        private val asyncHelper: AsyncHelper
) : TextSearchPageLoader {

    private var text: String = ""

    override fun updateSearchText(text: String) {
        this.text = text
    }

    override fun clearData() {
        adapter.clearData()
    }

    override fun getItemCount(): Int = adapter.itemsCount()

    override fun loadNextPage(
            page: Int,
            onSuccess: (totalPages: Int) -> Unit,
            onError: (e: Exception) -> Unit) {
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
