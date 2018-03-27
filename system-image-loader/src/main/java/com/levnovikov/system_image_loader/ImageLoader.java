package com.levnovikov.system_image_loader;

import android.widget.ImageView;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

public interface ImageLoader {

    void loadImage(ImageView imageView, String path);
}
