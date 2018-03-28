package com.levnovikov.feature_image_search.api.entities

/**
 * Author: lev.novikov
 * Date: 28/3/18.
 */

data class SearchImagesResponse(
        val photos: PhotosResponse,
        val stat: String
)

data class PhotosResponse(
        val page: Int,
        val pages: Int,
        val photo: List<ImageResponse>
)

data class ImageResponse(
        val id: String,
        val secret: String,
        val server: Int,
        val farm: Int
)