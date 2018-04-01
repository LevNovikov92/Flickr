package com.levnovikov.core_api.api.converter

import com.levnovikov.core_api.api.entities.ImageResponse
import com.levnovikov.core_api.api.entities.PhotosResponse
import com.levnovikov.core_api.api.entities.SearchImagesResponse
import org.json.JSONArray
import org.json.JSONObject
import kotlin.reflect.KClass

/**
 * Author: lev.novikov
 * Date: 28/3/18.
 */

class SearchImagesResponseConverter : ResponseConverter<SearchImagesResponse> {

    companion object {
        private const val PHOTOS = "photos"
        private const val STAT = "stat"
    }

    private val photosConverter = PhotosResponseConverter()

    override fun convertFrom(string: String): SearchImagesResponse =
            JSONObject(string).run {
                SearchImagesResponse(
                        photosConverter.convertFrom(getString(PHOTOS)),
                        getString(STAT))
            }

    override fun getEntityClass(): KClass<SearchImagesResponse> = SearchImagesResponse::class

}

class PhotosResponseConverter : ResponseConverter<PhotosResponse> {

    companion object {
        private const val PAGE = "page"
        private const val PAGES = "pages"
        private const val PHOTO = "photo"
    }

    private val imagesConverter = ImageResponseConverter()

    override fun getEntityClass(): KClass<PhotosResponse> = PhotosResponse::class

    override fun convertFrom(string: String): PhotosResponse =
            JSONObject(string).run {
                PhotosResponse(
                        getInt(PAGE),
                        getInt(PAGES),
                        getPhoto(getJSONArray(PHOTO))
                )
            }

    private fun getPhoto(arr: JSONArray): List<ImageResponse> =
            (0 until arr.length()).map { imagesConverter.convertFrom(arr.getString(it)) }
}

class ImageResponseConverter : ResponseConverter<ImageResponse> {

    companion object {
        private const val ID = "id"
        private const val SECRET = "secret"
        private const val SERVER = "server"
        private const val FARM = "farm"
    }

    override fun getEntityClass(): KClass<ImageResponse> = ImageResponse::class

    override fun convertFrom(string: String): ImageResponse =
            JSONObject(string).run {
                ImageResponse(
                        getString(ID),
                        getString(SECRET),
                        getInt(SERVER),
                        getInt(FARM)
                )
            }
}
