package com.levnovikov.core_network;

import com.levnovikov.core_network.request.Request;
import com.levnovikov.core_network.request.RequestBody;
import com.levnovikov.core_network.request.RequestTransformer;
import com.levnovikov.core_network.response.Response;
import com.levnovikov.core_network.response.ResponseBody;
import com.levnovikov.core_network.response.ResponseTransformer;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by lev.novikov
 * Date: 27/3/18.
 */
@SuppressWarnings("FieldCanBeLocal")
public class HttpClientImplTest {

    private final String baseUrl = "https://api.github.com";
    private final String reposPath = "/users/LevNovikov92/repos";

    private final String flickrRequestUrl= "https://api.flickr.com/services/rest";

    @Test
    public void makeFlickrCall() throws Exception {
        HttpClient client = new HttpClientImpl.Builder()
                .callExecutor(request -> {
                    if (request.getUrl().toString().equals(flickrRequestUrl + "?")) {
                        Response response = Mockito.mock(Response.class);
                        Mockito.when(response.getCode()).thenReturn(200);
                        return response;
                    } else {
                        Response response = Mockito.mock(Response.class);
                        Mockito.when(response.getCode()).thenReturn(404);
                        return response;
                    }
                })
                .build();
        Request request = new Request.Builder()
                .setUrl(flickrRequestUrl)
                .setMethod(Request.Method.GET)
                .build();
        Response response = client.makeCall(request);

        assertEquals(200, response.getCode());

        request = new Request.Builder().setUrl(flickrRequestUrl + "/wrong_path").build();
        response = client.makeCall(request);

        assertEquals(404, response.getCode());
    }

    @Test
    public void makeCall_transformer() throws Exception {
        RequestTransformer contentTransformer = request -> {
            RequestBody body = new RequestBody();
            body.setContent("content", "content-type");
            return new Request.Builder(request).setBody(body).build();
        };

        ResponseTransformer responseCodeTransformer = response -> {
            switch (response.getCode()) {
                case 200: {
                    ResponseBody body = response.getBody();
                    return new Response.Builder()
                            .body(body).code(500).build();
                }
                case 404: {
                    ResponseBody body = response.getBody();
                    return new Response.Builder()
                            .body(body).code(501).build();
                }
            }
            return response;
        };

        HttpClient client = new HttpClientImpl.Builder()
                .addTransformer(contentTransformer)
                .addTransformer(responseCodeTransformer)
                .callExecutor(request -> {
                    Response response = Mockito.mock(Response.class);
                    Mockito.when(response.getCode()).thenReturn(404);
                    Mockito.when(response.getBody())
                            .thenReturn(new ResponseBody(new ByteArrayInputStream("asd".getBytes()), "enc", "medType"));
                    return response;
                })
                .build();

        Request request = new Request.Builder()
                .setMethod(Request.Method.GET)
                .setUrl(baseUrl + reposPath)
                .build();
        Response response = client.makeCall(request);

        Assert.assertEquals(501, response.getCode());

        client = new HttpClientImpl.Builder()
                .addTransformer(contentTransformer)
                .addTransformer(responseCodeTransformer)
                .addTransformer((RequestTransformer) r -> {
                    Assert.assertEquals("content", r.getBody().getContent());
                    RequestBody body = r.getBody();
                    body.setContent("", "");
                    return new Request.Builder(r).setBody(body).build();
                })
                .build();

        request = new Request.Builder()
                .setMethod(Request.Method.GET)
                .setUrl(baseUrl + reposPath)
                .build();
        response = client.makeCall(request);
        Assert.assertEquals(500, response.getCode());
    }
}