package com.levnovikov.feature_image_search.di

import com.levnovikov.core_api.api.ImagesApi
import com.levnovikov.core_api.api.ImagesApiImpl
import com.levnovikov.system_async_helper.AsyncHelper
import com.levnovikov.core_network.api_provider.ApiProvider
import com.levnovikov.data_images.ImageRepoImpl
import com.levnovikov.data_images.ImagesRepo
import com.levnovikov.feature_image_search.ImageSearchPresenter
import com.levnovikov.feature_image_search.ImageSearchPresenterImpl
import com.levnovikov.feature_image_search.ImagesAdapter
import com.levnovikov.feature_image_search.ImagesAdapterImpl
import com.levnovikov.feature_image_search.SearchActivity
import com.levnovikov.feature_image_search.SearchScreenState
import com.levnovikov.feature_image_search.text_search_scroll_handler.ImageVOLoader
import com.levnovikov.feature_image_search.text_search_scroll_handler.ImageVOLoaderImpl
import com.levnovikov.feature_image_search.text_search_scroll_handler.ScrollHandlerFactory
import com.levnovikov.feature_image_search.text_search_scroll_handler.ScrollHandlerFactoryImpl
import com.levnovikov.feature_image_search.text_search_scroll_handler.TextSearchPageLoader
import com.levnovikov.feature_image_search.text_search_scroll_handler.TextSearchPageLoaderImpl
import com.levnovikov.system_image_loader.ImageLoader

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */
class ImageSearchModule(
        private val apiProvider: ApiProvider,
        imageLoader: ImageLoader,
        private val asyncHelper: AsyncHelper,
        private val activity: SearchActivity,
        private val state: SearchScreenState) {

    private fun getImagesRepo(): ImagesRepo = ImageRepoImpl(getImagesApi())

    private fun getImagesApi(): ImagesApi =
            ImagesApiImpl(apiProvider)

    private fun getScrollHandlerFactory(imageVOLoader: ImageVOLoader, adapter: ImagesAdapter): ScrollHandlerFactory =
            ScrollHandlerFactoryImpl(getPagerLoader(imageVOLoader, adapter), asyncHelper)

    private fun getPagerLoader(imageVOLoader: ImageVOLoader, adapter: ImagesAdapter): TextSearchPageLoader =
            TextSearchPageLoaderImpl(adapter, imageVOLoader, asyncHelper)

    private val adapter = ImagesAdapterImpl(activity.layoutInflater, imageLoader, asyncHelper)

    //Singleton
    fun getAdapter() = adapter

    fun getPresenter(): ImageSearchPresenter =
            ImageSearchPresenterImpl(activity, state, getScrollHandlerFactory(provideImageVOLoader(), getAdapter()))

    @Volatile
    private var imageVOLoader: ImageVOLoader? = null

    //Singleton
    private fun provideImageVOLoader(): ImageVOLoader {
        if (imageVOLoader == null) {
            synchronized(this) {
                if (imageVOLoader == null) {
                    imageVOLoader = ImageVOLoaderImpl(getImagesRepo())
                }
            }
        }
        return imageVOLoader!!
    }
}