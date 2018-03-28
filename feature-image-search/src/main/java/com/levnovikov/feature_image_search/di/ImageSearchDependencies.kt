package com.levnovikov.feature_image_search.di

import android.content.Context
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.core_network.api_provider.ApiProvider
import com.levnovikov.system_image_loader.ImageLoader

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */
interface ImageSearchDependencies {
    fun getAsyncHelper(): AsyncHelper
    fun getAppContext(): Context
    fun getApiProvider(): ApiProvider
    fun getImageLoader(): ImageLoader
}