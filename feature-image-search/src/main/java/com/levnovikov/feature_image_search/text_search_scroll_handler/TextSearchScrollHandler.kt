package com.levnovikov.feature_image_search.text_search_scroll_handler

import com.levnovikov.system_async_helper.AsyncHelper

import com.levnovikov.system_endless_scroll.EndlessScrollHandler
import com.levnovikov.system_endless_scroll.PageLoadingListener
import com.levnovikov.system_endless_scroll.PositionProvider
/**
 * Author: lev.novikov
 * Date: 25/5/18.
 */

interface TextSearchScrollHandler {
    fun reloadData(text: String)
    fun onScroll()
}

open class TextSearchScrollHandlerImpl(
        private val pageLoader: TextSearchPageLoader,
        pageLoadingListener: PageLoadingListener,
        positionProvider: PositionProvider,
        asyncHelper: AsyncHelper
) : EndlessScrollHandler(pageLoader, pageLoadingListener, positionProvider, asyncHelper),
        TextSearchScrollHandler {

    override fun reloadData(text: String) {
        pageLoader.updateSearchText(text)
        reloadData()
    }
}
