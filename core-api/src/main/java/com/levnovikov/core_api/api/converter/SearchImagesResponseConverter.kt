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

    override fun convertTo(entity: SearchImagesResponse): String =
            JSONObject().apply {
                put(PHOTOS, photosConverter.convertTo(entity.photos))
                put(STAT, entity.stat)
            }.toString()

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

    override fun convertTo(entity: PhotosResponse): String =
            JSONObject().apply {
                put(PAGE, entity.page)
                put(PAGES, entity.pages)
                put(PHOTO, JSONArray().apply { entity.photo.forEach { put(imagesConverter.convertTo(it)) } })
            }.toString()

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

    override fun convertTo(entity: ImageResponse): String =
            JSONObject().apply {
                put(ID, entity.id)
                put(SECRET, entity.secret)
                put(SERVER, entity.server)
                put(FARM, entity.farm)
            }.toString()

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
