package com.levnovikov.feature_image_search.di

import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.feature_image_search.SearchActivity
import com.levnovikov.system_image_loader.ImageLoader

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */
class ImageSearchComponent(
        private val activity: SearchActivity,
        private val dependencies: ImageSearchDependencies) {

    private val imageSearchModule = ImageSearchModule()

    fun getAsyncHelper(): AsyncHelper = dependencies.getAsyncHelper()

    fun getImageLoader(): ImageLoader = dependencies.getImageLoader()
}