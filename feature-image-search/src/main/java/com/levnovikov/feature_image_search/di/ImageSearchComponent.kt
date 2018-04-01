package com.levnovikov.feature_image_search.di

import com.levnovikov.feature_image_search.ImageSearchPresenter
import com.levnovikov.feature_image_search.SearchActivity
import com.levnovikov.feature_image_search.SearchScreenState

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */
class ImageSearchComponent(
        activity: SearchActivity,
        dependencies: ImageSearchDependencies,
        state: SearchScreenState) {

    private val imageSearchModule = ImageSearchModule(
            dependencies.getApiProvider(),
            dependencies.getImageLoader(),
            dependencies.getAsyncHelper(),
            activity,
            state)

    fun getPresenter(): ImageSearchPresenter = imageSearchModule.getPresenter()
}