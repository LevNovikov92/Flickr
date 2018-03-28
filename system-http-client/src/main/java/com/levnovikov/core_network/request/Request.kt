package com.levnovikov.core_network.request

import java.net.MalformedURLException
import java.net.URI
import java.net.URL
import java.util.HashMap

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class Request {

    var body = RequestBody()
        private set
    private lateinit var url: URL
    private val pathParams = HashMap<String, String>()
    private var method = Method.GET

    fun getUrl(): URL {
        try {
            val uri = url.toURI()
            var query = StringBuilder(if (uri.query == null) "" else uri.query)
            for ((key, value) in pathParams) {
                if (query.isEmpty()) {
                    query = StringBuilder(key + "=" + value)
                } else {
                    query.append("&").append(key).append("=").append(value)
                }
            }
            return URI(uri.scheme, uri.userInfo, uri.host, uri.port,
                    uri.path, query.toString(), uri.fragment).toURL()
        } catch (e: Exception) {
            throw RuntimeException("Wrong url")
        }

    }

    fun getMethod(): String {
        return method.name
    }

    enum class Method {
        GET,
        POST
    }

    class Builder {
        private val request = Request()

        constructor()

        constructor(request: Request) {
            this.request.method = request.method
            this.request.body = request.body.copy()
            this.request.url = request.url
            this.request.pathParams.putAll(request.pathParams)
        }

        @Throws(RuntimeException::class)
        fun setUrl(url: String): Builder {
            try {
                request.url = URL(url)
            } catch (e: MalformedURLException) {
                throw RuntimeException("URL is not correct")
            }

            return this
        }

        fun setBody(body: RequestBody?): Builder {
            request.body = body ?: RequestBody()
            return this
        }

        fun setMethod(method: Method): Builder {
            request.method = method
            return this
        }

        @Throws(RuntimeException::class)
        fun build(): Request {
            return request
        }

        fun setPathParams(pathParams: Map<String, String>?): Builder {
            request.pathParams.clear()
            if (pathParams != null) request.pathParams.putAll(pathParams)
            return this
        }

        fun addPathParam(key: String, value: String): Builder {
            request.pathParams[key] = value
            return this
        }
    }

}
