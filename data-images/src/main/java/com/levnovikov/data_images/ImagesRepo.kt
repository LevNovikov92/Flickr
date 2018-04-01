package com.levnovikov.data_images

import com.levnovikov.core_api.api.ImagesApi
import com.levnovikov.core_api.api.entities.SearchImagesResponse
import com.levnovikov.core_api.api.error.RequestException
import com.levnovikov.data_images.entities.Image
import com.levnovikov.data_images.entities.PagerData

/**
 * Author: lev.novikov
 * Date: 28/3/18.
 */
interface ImagesRepo {
    fun getImages(page: Int, text: String): Pair<PagerData, List<Image>>
}

class ImageRepoImpl(
        private val api: ImagesApi
) : ImagesRepo {

    @Throws(RequestException::class)
    override fun getImages(page: Int, text: String): Pair<PagerData, List<Image>> {
        val response = api.getImages(page, text)
        return getPagerData(response) to getImagesList(response)
    }

    private fun getPagerData(response: SearchImagesResponse): PagerData =
            PagerData(response.photos.page, response.photos.pages)

    private fun getImagesList(response: SearchImagesResponse): List<Image> =
            response.photos.photo.map {
                Image(it.id, it.secret, it.server, it.farm)
            }
}