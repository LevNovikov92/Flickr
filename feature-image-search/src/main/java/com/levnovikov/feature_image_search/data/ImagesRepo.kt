package com.levnovikov.feature_image_search.data

import com.levnovikov.feature_image_search.api.ImagesApi
import com.levnovikov.feature_image_search.api.entities.SearchImagesResponse
import com.levnovikov.feature_image_search.data.entities.Image
import com.levnovikov.feature_image_search.data.entities.PagerData

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