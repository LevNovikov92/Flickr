package com.levnovikov.feature_image_search.di

import com.levnovikov.core_api.api.ImagesApi
import com.levnovikov.core_api.api.ImagesApiImpl
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.core_network.api_provider.ApiProvider
import com.levnovikov.data_images.ImageRepoImpl
import com.levnovikov.data_images.ImagesRepo
import com.levnovikov.feature_image_search.ImageSearchPresenter
import com.levnovikov.feature_image_search.ImageSearchPresenterImpl
import com.levnovikov.feature_image_search.ImagesAdapterImpl
import com.levnovikov.feature_image_search.SearchActivity
import com.levnovikov.feature_image_search.SearchScreenState
import com.levnovikov.feature_image_search.scroll_handler.ImageVOLoader
import com.levnovikov.feature_image_search.scroll_handler.PageLoader
import com.levnovikov.feature_image_search.scroll_handler.ScrollHandlerFactory
import com.levnovikov.feature_image_search.scroll_handler.ScrollHandlerFactoryImpl
import com.levnovikov.system_image_loader.ImageLoader

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */
class ImageSearchModule(
        private val apiProvider: ApiProvider,
        private val imageLoader: ImageLoader,
        private val asyncHelper: AsyncHelper,
        private val activity: SearchActivity,
        private val state: SearchScreenState) {

    private fun getImagesRepo(): ImagesRepo = ImageRepoImpl(getImagesApi())

    private fun getImagesApi(): ImagesApi =
            ImagesApiImpl(apiProvider)

    private fun getScrollHandlerFactory(imageVOLoader: ImageVOLoader): ScrollHandlerFactory =
            ScrollHandlerFactoryImpl(getPagerLoader(imageVOLoader), asyncHelper)

    private fun getPagerLoader(imageVOLoader: ImageVOLoader): PageLoader =
            PageLoader(ImagesAdapterImpl(activity.layoutInflater, imageLoader, asyncHelper), imageVOLoader, asyncHelper)

    fun getPresenter(): ImageSearchPresenter =
            ImageSearchPresenterImpl(activity, imageLoader, getImagesRepo(), state, getScrollHandlerFactory())
}