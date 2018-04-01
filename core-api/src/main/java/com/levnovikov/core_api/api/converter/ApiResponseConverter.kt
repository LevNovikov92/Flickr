package com.levnovikov.core_api.api.converter

import com.levnovikov.core_network.api_provider.ResponseConverter
import kotlin.reflect.KClass

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class ApiResponseConverter : ResponseConverter {

    private val searchImageResponseConverter = SearchImagesResponseConverter()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> convertFrom(string: String, responseClass: KClass<T>): T {
        return when (responseClass) {
            searchImageResponseConverter.getEntityClass() -> searchImageResponseConverter.convertFrom(string) as T
            else -> throw UnsupportedOperationException("Not supported")
        }
    }
}
