package com.levnovikov.feature_image_search.api

import com.levnovikov.core_network.api_provider.ApiProvider
import com.levnovikov.core_network.request.Request
import com.levnovikov.feature_image_search.api.entities.SearchImagesResponse

/**
 * Author: lev.novikov
 * Date: 28/3/18.
 */

interface ImagesApi {
    fun getImages(page: Int, text: String): SearchImagesResponse
}

class ImagesApiImpl(
        private val apiProvider: ApiProvider
) : ImagesApi {

    override fun getImages(page: Int, text: String): SearchImagesResponse {
        val params = mapOf(
                "method" to "flickr.photos.search",
                "text" to text,
                "page" to page.toString(),
                "format" to "json",
                "nojsoncallback" to "1"
        )
        return apiProvider.makeRequest(Request.Method.GET, "/services/rest", null, params,
                SearchImagesResponse::class)
    }
}