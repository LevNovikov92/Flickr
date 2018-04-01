package com.levnovikov.core_network

import com.levnovikov.core_network.api_provider.ApiProvider
import com.levnovikov.core_network.api_provider.EntityConverter
import com.levnovikov.core_network.api_provider.ErrorConverter

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class ApiProviderFactory(
        private val client: HttpClient,
        private val baseUrl: String,
        private val errorConverter: ErrorConverter,
        private val converter: EntityConverter) {

    fun buildApiProvider(): ApiProvider {
        return ApiProvider.Builder()
                .baseUrl(baseUrl)
                .client(client)
                //                .contentType() TODO implement
                .converter(converter)
                .errorConverter(errorConverter)
                .build()
    }
}
