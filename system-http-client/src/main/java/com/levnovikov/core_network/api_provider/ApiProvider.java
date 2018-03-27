package com.levnovikov.core_network.api_provider;

import com.google.common.annotations.VisibleForTesting;
import com.levnovikov.core_network.HttpClient;
import com.levnovikov.core_network.request.Request;
import com.levnovikov.core_network.request.RequestBody;
import com.levnovikov.core_network.response.Response;

import java.io.IOException;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.annotations.Nullable;

/**
 * Created by lev.novikov
 * Date: 23/2/18.
 */

public class ApiProvider {

    private String baseUrl;
    private EntityConverter converter;
    private HttpClient client;
    private String contentType;

    private ApiProvider() {
    }

    public <Rq, Rs> Single<Rs> makeRequest(
            Request.Method method,
            String path, Rq requestEntity, Class<Rs> responseClass) {
        return client.makeCall(getRequest(method, path, requestEntity, null))
                .map(rsp -> getResponse(rsp, responseClass));

    }

    private <T> Request getRequest(Request.Method method, String path, T request, @Nullable Map<String, String> pathParams) {
        RequestBody body = new RequestBody(converter.convertTo(request), contentType);
        return new Request.Builder()
                .setMethod(method)
                .setPathParams(pathParams)
                .setUrl(baseUrl + path)
                .setBody(body)
                .build();
    }

    private <R> R getResponse(Response rsp, Class<R> responseClass) throws IOException {
        String body = rsp.getBody().getContentString();
        return converter.convertFrom(body, responseClass);
    }

    public static class Builder {
        private final ApiProvider provider = new ApiProvider();

        public Builder baseUrl(String baseUrl) {
            provider.baseUrl = baseUrl;
            return this;
        }

        public Builder converter(EntityConverter converter) {
            provider.converter = converter;
            return this;
        }

        public Builder client(HttpClient client) {
            provider.client = client;
            return this;
        }

        public Builder contentType(String contentType) {
            provider.contentType = contentType;
            return this;
        }

        public ApiProvider build() {
            return provider;
        }
    }
}
