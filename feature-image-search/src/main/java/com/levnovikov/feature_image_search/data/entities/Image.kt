package com.levnovikov.feature_image_search.data.entities

/**
 * Author: lev.novikov
 * Date: 28/3/18.
 */
data class Image(
        val id: String,
        val secret: String,
        val server: Int,
        val farm: Int
)