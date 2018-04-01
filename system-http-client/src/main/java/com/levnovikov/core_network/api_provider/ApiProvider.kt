package com.levnovikov.core_network.api_provider

import com.levnovikov.core_network.HttpClient
import com.levnovikov.core_network.exceptions.RequestError
import com.levnovikov.core_network.request.Request
import com.levnovikov.core_network.request.RequestBody
import com.levnovikov.core_network.response.Response
import org.json.JSONException
import java.io.IOException
import kotlin.reflect.KClass

/**
 * Created by lev.novikov
 * Date: 23/2/18.
 */

class ApiProvider private constructor() {

    private lateinit var baseUrl: String
    private lateinit var responseConverter: ResponseConverter
    private lateinit var requestConverter: RequestConverter
    private lateinit var errorConverter: ErrorConverter
    private lateinit var client: HttpClient
    private lateinit var contentType: String

    @Throws(RequestError::class)
    fun <Rq : Any, Rs : Any> makeRequest(
            method: Request.Method,
            path: String, requestEntity: Rq?, pathParams: Map<String, String>?, responseClass: KClass<Rs>): Rs {
        return getResponse(
                client.makeCall(
                        getRequest(method, path, requestEntity, pathParams)), responseClass)

    }

    private fun <T : Any> getRequest(method: Request.Method, path: String, request: T?, pathParams: Map<String, String>?): Request {
        return Request.Builder()
                .setMethod(method)
                .setPathParams(pathParams)
                .setUrl(baseUrl + path)
                .setBody(if (request != null) RequestBody(requestConverter.convertTo(request), contentType) else null)
                .build()
    }

    @Throws(IOException::class, RequestError::class)
    private fun <R : Any> getResponse(rsp: Response, responseClass: KClass<R>): R {
        val body = rsp.body.contentString
        try {
            return responseConverter.convertFrom(body, responseClass)
        } catch (e: JSONException) {
            throw RequestError(errorConverter.convertFrom(body))
        }
    }

    class Builder {
        private val provider = ApiProvider()

        fun baseUrl(baseUrl: String): Builder {
            provider.baseUrl = baseUrl
            return this
        }

        fun converter(requestConverter: RequestConverter, responseConverter: ResponseConverter): Builder {
            provider.requestConverter = requestConverter
            provider.responseConverter = responseConverter
            return this
        }

        fun errorConverter(converter: ErrorConverter): Builder {
            provider.errorConverter = converter
            return this
        }

        fun client(client: HttpClient): Builder {
            provider.client = client
            return this
        }

        fun contentType(contentType: String): Builder {
            provider.contentType = contentType
            return this
        }

        fun build(): ApiProvider {
            return provider
        }
    }
}
