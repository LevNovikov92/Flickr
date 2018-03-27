package com.levnovikov.feature_image_search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.levnovikov.core_network.HttpClient;
import com.levnovikov.core_network.HttpClientImpl;
import com.levnovikov.core_network.UrlConnectionCallExecutor;
import com.levnovikov.system_image_loader.ImageLoader;
import com.levnovikov.system_image_loader.ImageLoaderImpl;

import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        loadImage();
    }

    private void loadImage() {
        HttpClient client = new HttpClientImpl.Builder()
                .callExecutor(new UrlConnectionCallExecutor())
                .threadComposer(upstream -> upstream.subscribeOn(Schedulers.io()))
                .build();

        ImageLoader loader = new ImageLoaderImpl.Builder()
                .setClient(client)
                .setBaseUrl("http://farm1.static.flickr.com")
                .setCache(getCacheDir())
                .build();

        String path = "/866/26171503967_f609bf5709.jpg";
        loader.loadImage(findViewById(R.id.imageView), path);
    }
}
