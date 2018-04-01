package com.levnovikov.data_images.entities

/**
 * Author: lev.novikov
 * Date: 28/3/18.
 */
data class Image(
        val id: String,
        private val secret: String,
        private val server: Int,
        val farm: Int
) {
    fun getPath() = "/$server/${id}_$secret"
}