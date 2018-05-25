package com.levnovikov.feature_image_search.text_search_scroll_handler

import com.levnovikov.system_async_helper.AsyncHelper
import com.levnovikov.system_endless_scroll.PageLoadingListener
import com.levnovikov.system_endless_scroll.PositionProvider

/**
 * Author: lev.novikov
 * Date: 18/3/18.
 */

interface ScrollHandlerFactory {
    fun getEndlessScrollHandler(
            pageLoadingListener: PageLoadingListener,
            positionProvider: PositionProvider): TextSearchScrollHandler
}

class ScrollHandlerFactoryImpl (
        private val pageLoader: TextSearchPageLoader,
        private val asyncHelper: AsyncHelper
) : ScrollHandlerFactory {

    override fun getEndlessScrollHandler(
            pageLoadingListener: PageLoadingListener,
            positionProvider: PositionProvider
    ): TextSearchScrollHandler =
            TextSearchScrollHandlerImpl(
                    pageLoader,
                    pageLoadingListener,
                    positionProvider,
                    asyncHelper)
}