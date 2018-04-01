package com.levnovikov.core_network
import com.levnovikov.core_network.request.Request
import com.levnovikov.core_network.response.Response
import com.levnovikov.core_network.response.ResponseBody

import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection

/**
 * Created by lev.novikov
 * Date: 27/3/18.
 */

class UrlConnectionCallExecutor : CallExecutor {

    @Throws(IOException::class)
    override fun execute(request: Request): Response {
        val connection = request.getUrl().openConnection() as HttpURLConnection
        connection.requestMethod = request.getMethod()
        if (request.body.isNotEmpty) {
            connection.doOutput = true
            val outputStream = connection.outputStream
            outputStream.write(request.body.content.toByteArray())
        }
        connection.connect()
        return getResponse(connection)
    }

    @Throws(IOException::class)
    private fun getResponse(connection: HttpURLConnection): Response {
        return Response.Builder()
                .code(connection.responseCode)
                .body(getResponseBody(connection)).build()
    }

    private fun getResponseBody(connection: HttpURLConnection): ResponseBody {
        var stream: InputStream? = null
        try {
            stream = try {
                connection.inputStream
            } catch (e: IOException) {
                connection.errorStream
            }

            val contentType = connection.contentType
            var encoding = "UTF-8"
            var mediaType = "text/html"
            if (contentType != null) {
                encoding = getCharset(contentType)
                mediaType = getMediaType(contentType)
            }
            return ResponseBody(stream, encoding, mediaType)
        } catch (e: Exception) {
            stream?.close()
            throw e
        }
    }

    private fun getCharset(contentType: String): String {
        //TODO not implemented
        return "UTF-8"
    }

    private fun getMediaType(contentType: String): String {
        //TODO not implemented
        return "text/html"
    }
}
