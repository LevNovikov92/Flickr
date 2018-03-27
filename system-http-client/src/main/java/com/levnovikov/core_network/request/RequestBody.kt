package com.levnovikov.core_network.request

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class RequestBody {

    var content = ""
        private set
    private var contentType = ""

    val isNotEmpty: Boolean
        get() = !content.isEmpty()

    constructor()

    constructor(content: String, contentType: String) {
        this.content = content
        this.contentType = contentType
    }

    fun setContent(content: String, contentType: String) {
        this.content = content
        this.contentType = contentType
    }

    fun copy(): RequestBody {
        val body = RequestBody()
        body.content = content
        body.contentType = contentType
        return body
    }
}
