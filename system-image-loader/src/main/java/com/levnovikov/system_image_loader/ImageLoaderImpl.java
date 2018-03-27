package com.levnovikov.system_image_loader;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.levnovikov.core_network.HttpClient;
import com.levnovikov.core_network.request.Request;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

public class ImageLoaderImpl implements ImageLoader {

    private HttpClient client;
    private String baseUrl;
    private File cache;

    private ImageLoaderImpl() {
    }

    @Override
    public void loadImage(ImageView imageView, String path) {
        saveImageInInternalStorage(path)
                .map(img -> BitmapFactory.decodeFile(img.getAbsolutePath()))
                .observeOn(AndroidSchedulers.mainThread())
                .map(bitmap -> {
                    imageView.setImageBitmap(bitmap);
                    return bitmap;
                })
                .subscribe(img -> {
                    Log.i(">>>IMAGE", "Image " + path + "is loaded");
                }, e -> {
                    e.printStackTrace();
                    throw new RuntimeException();
                });
    }

    private Single<File> saveImageInInternalStorage(String path) {
        Request request = new Request.Builder()
                .setMethod(Request.Method.GET)
                .setUrl(baseUrl + path)
                .build();
        return client.makeCall(request)
                .map(response -> {
                    File img = new File(cache, path.replace('/', '_'));
                    if (!img.exists()) {
                        Files.copy(response.getBody().getContentStream(), img.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                    return img;
                });
    }

    public static class Builder {
        private ImageLoaderImpl loader = new ImageLoaderImpl();

        public Builder setClient(HttpClient client) {
            loader.client = client;
            return this;
        }

        public Builder setBaseUrl(String baseUrl) {
            loader.baseUrl = baseUrl;
            return this;
        }

        public Builder setCache(File dir) {
            if (!dir.isDirectory()) throw new UnsupportedOperationException("Cache should be a directory");
            loader.cache = dir;
            return this;
        }

        public ImageLoader build() {
            validateLoader();
            return loader;
        }

        private void validateLoader() {
            if (loader.cache == null) throw new UnsupportedOperationException("Cache should be specified");
            if (loader.client == null) throw new UnsupportedOperationException("HttpClient should be specified");
            if (loader.baseUrl == null) throw new UnsupportedOperationException("Url should be specified");
        }
    }

}
