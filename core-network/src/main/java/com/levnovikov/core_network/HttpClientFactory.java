package com.levnovikov.core_network;

import com.levnovikov.core_network.composers.ImageLoaderThreadComposer;
import com.levnovikov.core_network.composers.NetworkThreadComposer;
import com.levnovikov.core_network.transformers.AuthTransformer;

import javax.inject.Inject;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

public class HttpClientFactory {

    private final AuthTransformer authTransformer;

    @Inject
    HttpClientFactory(AuthTransformer authTransformer) {
        this.authTransformer = authTransformer;
    }

    public HttpClient buildNetworkClient() {
        return new HttpClientImpl.Builder()
                .threadComposer(new NetworkThreadComposer())
                .callExecutor(new UrlConnectionCallExecutor())
                .addTransformer(authTransformer)
                .build();
    }

    public HttpClient buildImageLoaderClient() {
        return new HttpClientImpl.Builder()
                .threadComposer(new ImageLoaderThreadComposer())
                .callExecutor(new UrlConnectionCallExecutor())
                .build();
    }
}
