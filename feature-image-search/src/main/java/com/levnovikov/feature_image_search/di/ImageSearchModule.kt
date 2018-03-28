package com.levnovikov.feature_image_search.di

import com.levnovikov.core_network.api_provider.ApiProvider
import com.levnovikov.feature_image_search.api.ImagesApi
import com.levnovikov.feature_image_search.api.ImagesApiImpl
import com.levnovikov.feature_image_search.data.ImageRepoImpl
import com.levnovikov.feature_image_search.data.ImagesRepo

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */
class ImageSearchModule(private val apiProvider: ApiProvider) {

    fun getImagesRepo(): ImagesRepo = ImageRepoImpl(getImagesApi())

    private fun getImagesApi(): ImagesApi =
            ImagesApiImpl(apiProvider)
}