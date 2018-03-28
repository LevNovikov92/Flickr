package com.levnovikov.feature_image_search.ui.scroll_handler

import com.levnovikov.feature_image_search.ui.ImagesAdapter

interface ScrollHandler {
    fun reloadData(text: String)
    fun onScroll()
    fun getAdapter(): ImagesAdapter
}