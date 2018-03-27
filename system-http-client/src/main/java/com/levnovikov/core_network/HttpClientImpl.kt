package com.levnovikov.core_network

import com.levnovikov.core_network.request.Request
import com.levnovikov.core_network.request.RequestTransformer
import com.levnovikov.core_network.response.Response
import com.levnovikov.core_network.response.ResponseTransformer

import java.util.ArrayList

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class HttpClientImpl private constructor() : HttpClient {

    private val requestTransformers = ArrayList<RequestTransformer>()
    private val responseTransformers = ArrayList<ResponseTransformer>()
    private lateinit var callExecutor: CallExecutor
//    private lateinit var threadComposer: SingleTransformer<Response, Response> TODO solve

    override fun makeCall(request: Request): Response {
        return applyResponseTransformers(callExecutor.execute(
                applyRequestTransformers(request)))
    }

    private fun applyRequestTransformers(request: Request): Request {
        var req = request
        requestTransformers.forEach {
            req = it.transform(req)
        }
        return req
    }

    private fun applyResponseTransformers(response: Response): Response {
        var resp = response
        responseTransformers.forEach {
            resp = it.transform(resp)
        }
        return resp
    }

    class Builder {
        private val client = HttpClientImpl()

        init {
            client.callExecutor = UrlConnectionCallExecutor()
        }

//        fun threadComposer(threadComposer: SingleTransformer<Response, Response>): Builder { TODO
//            client.threadComposer = threadComposer
//            return this
//        }

        fun callExecutor(callExecutor: CallExecutor): Builder {
            client.callExecutor = callExecutor
            return this
        }

        fun addTransformer(transformer: RequestTransformer): Builder {
            client.requestTransformers.add(transformer)
            return this
        }

        fun addTransformer(transformer: ResponseTransformer): Builder {
            client.responseTransformers.add(transformer)
            return this
        }

        fun build(): HttpClient {
            return client
        }
    }
}
