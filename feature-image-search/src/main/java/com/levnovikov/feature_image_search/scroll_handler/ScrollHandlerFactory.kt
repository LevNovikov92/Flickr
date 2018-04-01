package com.levnovikov.feature_image_search.scroll_handler

import android.view.LayoutInflater
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.feature_image_search.ImageSearchView
import com.levnovikov.feature_image_search.ImagesAdapterImpl
import com.levnovikov.system_image_loader.ImageLoader

/**
 * Author: lev.novikov
 * Date: 18/3/18.
 */
class ScrollHandlerFactory constructor(
        private val asyncHelper: AsyncHelper,
        private val imageLoader: ImageLoader,
        private val inflater: LayoutInflater,
        private val view: ImageSearchView
) {

    fun getEndlessScrollHandler(
            imageVOLoader: ImageVOLoader,
            pageLoadingListener: PageLoadingListener
    ): ScrollHandler =
            EndlessScrollHandler(ImagesAdapterImpl(inflater, imageLoader, asyncHelper),
                    imageVOLoader, pageLoadingListener, asyncHelper, view)
}