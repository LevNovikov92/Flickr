package com.levnovikov.core_network;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;
import com.levnovikov.core_network.request.Request;
import com.levnovikov.core_network.response.Response;
import com.levnovikov.core_network.response.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Created by lev.novikov
 * Date: 27/3/18.
 */

public class UrlConnectionCallExecutor implements CallExecutor {

    @Override
    public Response execute(Request request) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) request.getUrl().openConnection();
        connection.setRequestMethod(request.getMethod());
        if (request.getBody().isNotEmpty()) {
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(request.getBody().getContent().getBytes());
        }
        connection.connect();
        return getResponse(connection);
    }

    @VisibleForTesting
    Response getResponse(HttpURLConnection connection) throws IOException {
        return new Response.Builder()
                .code(connection.getResponseCode())
                .body(getResponseBody(connection)).build();
    }

    @VisibleForTesting
    ResponseBody getResponseBody(HttpURLConnection connection) {
        InputStream stream;
        try {
            stream = connection.getInputStream();
        } catch (IOException e) {
            stream = connection.getErrorStream();
        }

        String contentType = connection.getContentType();
        String encoding = Charsets.UTF_8.toString();
        String mediaType = "";
        if (contentType != null) {
             encoding = getCharset(contentType);
             mediaType = getMediaType(contentType);
        }
        return new ResponseBody(stream, encoding, mediaType);
    }

    private String getCharset(String contentType) {
        //TODO not implemented
        return Charsets.UTF_8.toString();
    }

    private String getMediaType(String contentType) {
        //TODO not implemented
        return "text/html";
    }
}
