package com.levnovikov.core_network.response

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class Response private constructor() {

    var code: Int = 0
        private set
    lateinit var body: ResponseBody

    class Builder {
        private val response = Response()

        fun code(code: Int): Builder {
            response.code = code
            return this
        }

        fun body(body: ResponseBody): Builder {
            response.body = body
            return this
        }

        fun build(): Response {
            return response
        }
    }
}
