package com.levnovikov.core_network.response

import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class ResponseBody(val contentStream: InputStream, val encoding: String, val mediaType: String) {

    val contentString: String
        @Throws(IOException::class)
        get() = readStringFromStream(contentStream)

    @Throws(IOException::class)
    private fun readStringFromStream(input: InputStream): String {
        return input.bufferedReader(Charset.forName(encoding)).use { it.readText() }
    }
}
