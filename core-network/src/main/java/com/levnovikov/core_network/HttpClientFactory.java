package com.levnovikov.core_network;

import com.levnovikov.core_network.transformers.AuthTransformer;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

public class HttpClientFactory {

    private final AuthTransformer authTransformer;

    HttpClientFactory(AuthTransformer authTransformer) {
        this.authTransformer = authTransformer;
    }

    public HttpClient buildNetworkClient() {
        return new HttpClientImpl.Builder()
//                .threadComposer(new NetworkThreadComposer()) TODO solve
                .callExecutor(new UrlConnectionCallExecutor())
                .addTransformer(authTransformer)
                .build();
    }

    public HttpClient buildImageLoaderClient() {
        return new HttpClientImpl.Builder()
//                .threadComposer(new ImageLoaderThreadComposer()) TODO solve
                .callExecutor(new UrlConnectionCallExecutor())
                .build();
    }
}
