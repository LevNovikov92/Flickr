package com.levnovikov.core_network;

import com.levnovikov.core_network.request.Request;
import com.levnovikov.core_network.request.RequestBody;
import com.levnovikov.core_network.request.RequestTransformer;
import com.levnovikov.core_network.response.Response;
import com.levnovikov.core_network.response.ResponseBody;
import com.levnovikov.core_network.response.ResponseTransformer;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by lev.novikov
 * Date: 27/3/18.
 */

/**
 * NOTE: This test is calling real api. For debug purpose only. WILL NOT BE USED IN PROD.
 */

@SuppressWarnings("FieldCanBeLocal")
public class HttpClientImplTestDebug {

    private final String baseUrl = "https://api.github.com";
    private final String reposPath = "/users/LevNovikov92/repos";

    private final String flickrRequestUrl= "https://api.flickr.com/services/rest";

    @Test
    public void makeFlickrCall() throws Exception {
        HttpClient client = new HttpClientImpl.Builder()
                .build();
        Map<String, String> params = new HashMap<>();
        params.put("method", "flickr.photos.search");
        params.put("api_key", "fe1d3418135ddd69c31f5630b8c521e3");
        params.put("text", "kittens");
        params.put("format", "json");
        params.put("nojsoncallback", "1");
        params.put("api_sig", "a587cd42245f85135254d6acdd61ef4e");
        Request request = new Request.Builder()
                .setUrl(flickrRequestUrl)
                .setMethod(Request.Method.GET)
                .setPathParams(params)
                .build();
        Response response = client.makeCall(request);

        assertEquals(200, response.getCode());

        System.out.println(response.getBody().getContentString());
        printDivider();

        request = new Request.Builder().setUrl(flickrRequestUrl + "/wrong_path").build();
        response = client.makeCall(request);

        assertEquals(404, response.getCode());
        System.out.println(response.getBody().getContentString());
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

    private void printDivider() {
        System.out.println();
        System.out.println();
        System.out.println("****************************************************************************");
        System.out.println();
        System.out.println();
    }
}