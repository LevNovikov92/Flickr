package com.levnovikov.feature_image_search.di

import com.levnovikov.feature_image_search.ui.ImageSearchPresenter
import com.levnovikov.feature_image_search.ui.SearchActivity

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */
class ImageSearchComponent(
        activity: SearchActivity,
        dependencies: ImageSearchDependencies) {

    private val imageSearchModule = ImageSearchModule(
            dependencies.getApiProvider(),
            dependencies.getImageLoader(),
            dependencies.getAsyncHelper(),
            activity)

    fun getPresenter(): ImageSearchPresenter = imageSearchModule.getPresenter()
}