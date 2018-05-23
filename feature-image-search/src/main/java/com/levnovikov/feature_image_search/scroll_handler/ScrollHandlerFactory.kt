package com.levnovikov.feature_image_search.scroll_handler

import com.levnovikov.core_common.AsyncHelper

/**
 * Author: lev.novikov
 * Date: 18/3/18.
 */

interface ScrollHandlerFactory {
    fun getEndlessScrollHandler(
            pageLoadingListener: PageLoadingListener,
            positionProvider: PositionProvider): ScrollHandler
}

class ScrollHandlerFactoryImpl (
        private val pageLoader: PageLoader,
        private val asyncHelper: AsyncHelper
) : ScrollHandlerFactory {

    override fun getEndlessScrollHandler(
            pageLoadingListener: PageLoadingListener,
            positionProvider: PositionProvider
    ): ScrollHandler =
            EndlessScrollHandler(
                    pageLoader,
                    pageLoadingListener,
                    positionProvider,
                    asyncHelper)
}