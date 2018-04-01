package com.levnovikov.flickr.di

import android.content.Context
import com.levnovikov.core_api.api.converter.ApiResponseConverter
import com.levnovikov.core_api.api.converter.ErrorResponseConverter
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.core_network.di.NetworkModule
import com.levnovikov.feature_image_search.di.ImageSearchDependencies
import com.levnovikov.system_image_loader.ImageLoader

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class AppComponent(context: Context) : ImageSearchDependencies {

    private val networkModule = NetworkModule(context.cacheDir, ApiResponseConverter(), ErrorResponseConverter())
    private val appModule = AppModule(context)
    private val threadingModule = ThreadingModule(context)

    override fun getAsyncHelper(): AsyncHelper =
            threadingModule.getAsyncHelper()

    override fun getAppContext(): Context =
            appModule.getAppContext()

    override fun getApiProvider() =
            networkModule.getApiProvider()

    override fun getImageLoader(): ImageLoader =
            networkModule.getImageLoader()

}

