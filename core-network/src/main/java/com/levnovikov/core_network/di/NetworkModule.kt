package com.levnovikov.core_network.di

import com.levnovikov.core_network.ApiProviderFactory
import com.levnovikov.core_network.HttpClient
import com.levnovikov.core_network.HttpClientFactory
import com.levnovikov.core_network.api_provider.EntityConverter
import com.levnovikov.core_network.api_provider.ErrorConverter
import com.levnovikov.core_network.transformers.AuthTransformer
import com.levnovikov.system_image_loader.ImageLoader
import com.levnovikov.system_image_loader.ImageLoaderFactory
import java.io.File

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class NetworkModule(
        private val cache: File,
        private val converter: EntityConverter,
        private val errorConverter: ErrorConverter) {

    private fun getApiKey(): String { //TODO move to app module
        return "3e7cc266ae2b0e0d78e279ce8e361736"
    }

    private fun getBaseApiUrl(): String { //TODO move to app module
        return "https://api.flickr.com"
    }

    private fun getApiFactory(): ApiProviderFactory {
        return ApiProviderFactory(getNetworkClient(), getBaseApiUrl(), errorConverter, converter)
    }

    private fun getHttpClientFactory(): HttpClientFactory {
        return HttpClientFactory(AuthTransformer(getApiKey()))
    }

    private fun getNetworkClient(): HttpClient {
        return getHttpClientFactory().buildNetworkClient()
    }

    private fun getImageLoaderUrl(): String { //TODO move to app module
        return "static.flickr.com"
    }

    private fun getImageLoaderClient(): HttpClient {
        return getHttpClientFactory().buildImageLoaderClient()
    }

    fun getImageLoader(): ImageLoader {
        return ImageLoaderFactory(getImageLoaderUrl(), cache, getImageLoaderClient()).buildImageLoader()
    }

    fun getApiProvider() = getApiFactory().buildApiProvider()
}
