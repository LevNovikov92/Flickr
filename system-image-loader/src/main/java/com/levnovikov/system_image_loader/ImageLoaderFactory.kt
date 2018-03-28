package com.levnovikov.system_image_loader

import com.levnovikov.core_network.HttpClient
import java.io.File

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */
class ImageLoaderFactory(
        private val baseUrl: String,
        private val cache: File,
        private val client: HttpClient
) {

    fun buildImageLoader(): ImageLoader =
            ImageLoaderImpl.Builder()
                    .setBaseUrl(baseUrl)
                    .setCache(cache)
                    .setClient(client)
                    .build()
}