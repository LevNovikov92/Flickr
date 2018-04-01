package com.levnovikov.core_network

import com.levnovikov.core_network.transformers.AuthTransformer

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class HttpClientFactory internal constructor(private val authTransformer: AuthTransformer) {

    fun buildNetworkClient(): HttpClient {
        return HttpClientImpl.Builder()
                .callExecutor(UrlConnectionCallExecutor())
                .addTransformer(authTransformer)
                .build()
    }

    fun buildImageLoaderClient(): HttpClient {
        return HttpClientImpl.Builder()
                .callExecutor(UrlConnectionCallExecutor())
                .build()
    }
}
