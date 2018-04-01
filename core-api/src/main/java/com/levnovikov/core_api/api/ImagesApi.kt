package com.levnovikov.core_api.api

import com.levnovikov.core_api.api.entities.ErrorResponse
import com.levnovikov.core_api.api.entities.SearchImagesResponse
import com.levnovikov.core_api.api.error.RequestException
import com.levnovikov.core_network.api_provider.ApiProvider
import com.levnovikov.core_network.exceptions.RequestError
import com.levnovikov.core_network.request.Request

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

    @Throws(RequestException::class)
    override fun getImages(page: Int, text: String): SearchImagesResponse {
        val params = mapOf(
                "method" to "flickr.photos.search",
                "text" to text,
                "page" to page.toString(),
                "format" to "json",
                "nojsoncallback" to "1"
        )
        try {
            return apiProvider.makeRequest(Request.Method.GET, "/services/rest", null, params,
                    SearchImagesResponse::class)
        } catch (e: RequestError) {
            val error = e.error
            throw if (error is ErrorResponse) { RequestException(error.code, error.message!!) } else e
        }
    }
}