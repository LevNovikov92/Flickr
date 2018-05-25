package com.levnovikov.feature_image_search.text_search_scroll_handler

import com.levnovikov.core_api.api.error.RequestException
import com.levnovikov.data_images.ImagesRepo
import com.levnovikov.data_images.entities.PagerData
import com.levnovikov.feature_image_search.ImageVO

interface ImageVOLoader {
    fun loadVO(page: Int, text: String): Pair<List<ImageVO>, PagerData>
}

class ImageVOLoaderImpl(
        private val imagesRepo: ImagesRepo
) : ImageVOLoader {

    @Throws(RequestException::class)
    override fun loadVO(page: Int, text: String): Pair<List<ImageVO>, PagerData> {
        return imagesRepo.getImages(page, text).run {
            return@run second.map { ImageVO(it.getPath(), it.farm) } to first
        }
    }
}