package com.levnovikov.system_image_loader

import android.graphics.Bitmap

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

interface ImageLoader {

    fun loadImage(path: String, farm: Int): Bitmap?
}
