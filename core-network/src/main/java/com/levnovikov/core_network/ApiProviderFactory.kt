package com.levnovikov.core_network

import com.levnovikov.core_network.api_provider.ApiProvider
import com.levnovikov.core_network.converter.GsonConverter

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class ApiProviderFactory(
        private val client: HttpClient,
        private val baseUrl: String) {

    fun buildApiProvider(): ApiProvider {
        return ApiProvider.Builder()
                .baseUrl(baseUrl)
                .client(client)
                //                .contentType() TODO implement
                .converter(GsonConverter())
                .build()
    }
}
