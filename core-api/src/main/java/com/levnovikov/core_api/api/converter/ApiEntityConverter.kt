package com.levnovikov.core_api.api.converter

import com.levnovikov.core_api.api.entities.SearchImagesResponse
import com.levnovikov.core_network.api_provider.EntityConverter
import kotlin.reflect.KClass

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class ApiEntityConverter : EntityConverter {

    private val searchImageResponseConverter = SearchImagesResponseConverter()

    override fun <T : Any> convertTo(entity: T): String =
            when(entity::class) {
                searchImageResponseConverter.getEntityClass() -> searchImageResponseConverter
                        .convertTo(entity as SearchImagesResponse)
                else -> throw UnsupportedOperationException("Not supported")
            }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> convertFrom(string: String, responseClass: KClass<T>): T {
        return when (responseClass) {
            searchImageResponseConverter.getEntityClass() -> searchImageResponseConverter.convertFrom(string) as T
            else -> throw UnsupportedOperationException("Not supported")
        }
    }
}
