package com.levnovikov.feature_image_search.di

import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.core_network.api_provider.ApiProvider
import com.levnovikov.feature_image_search.api.ImagesApi
import com.levnovikov.feature_image_search.api.ImagesApiImpl
import com.levnovikov.feature_image_search.data.ImageRepoImpl
import com.levnovikov.feature_image_search.data.ImagesRepo
import com.levnovikov.feature_image_search.ui.ImageSearchPresenter
import com.levnovikov.feature_image_search.ui.ImageSearchPresenterImpl
import com.levnovikov.feature_image_search.ui.SearchActivity
import com.levnovikov.feature_image_search.ui.scroll_handler.ScrollHandlerFactory
import com.levnovikov.system_image_loader.ImageLoader

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */
class ImageSearchModule(
        private val apiProvider: ApiProvider,
        private val imageLoader: ImageLoader,
        private val asyncHelper: AsyncHelper,
        private val activity: SearchActivity) {

    fun getImagesRepo(): ImagesRepo = ImageRepoImpl(getImagesApi())

    private fun getImagesApi(): ImagesApi =
            ImagesApiImpl(apiProvider)

    private fun getScrollHandlerFactory(): ScrollHandlerFactory =
            ScrollHandlerFactory(asyncHelper, imageLoader, activity.layoutInflater, activity)

    fun getPresenter(): ImageSearchPresenter =
            ImageSearchPresenterImpl(activity, getImagesRepo(), getScrollHandlerFactory())
}