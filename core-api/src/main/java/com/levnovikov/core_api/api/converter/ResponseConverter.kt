package com.levnovikov.core_api.api.converter

import kotlin.reflect.KClass

interface ResponseConverter<T : Any> {
    fun convertTo(entity: T): String
    fun convertFrom(string: String): T
    fun getEntityClass(): KClass<T>
}