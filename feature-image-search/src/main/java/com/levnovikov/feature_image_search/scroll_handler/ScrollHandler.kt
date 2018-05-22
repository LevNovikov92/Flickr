package com.levnovikov.feature_image_search.scroll_handler

import com.levnovikov.feature_image_search.ImagesAdapter

interface ScrollHandler {
    fun reloadData(text: String)
    fun onScroll()
}