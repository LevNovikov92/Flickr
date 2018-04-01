package com.levnovikov.core_network

import com.levnovikov.core_network.api_provider.ApiProvider
import com.levnovikov.core_network.api_provider.ResponseConverter
import com.levnovikov.core_network.api_provider.ErrorConverter
import com.levnovikov.core_network.api_provider.RequestConverter

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class ApiProviderFactory(
        private val client: HttpClient,
        private val baseUrl: String,
        private val errorConverter: ErrorConverter,
        private val requestConverter: RequestConverter,
        private val responseConverter: ResponseConverter) {

    fun buildApiProvider(): ApiProvider {
        return ApiProvider.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .converter(requestConverter, responseConverter)
                .errorConverter(errorConverter)
                .build()
    }
}
