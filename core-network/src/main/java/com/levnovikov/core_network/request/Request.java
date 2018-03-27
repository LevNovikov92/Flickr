package com.levnovikov.core_network.request;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

public class Request {

    private RequestBody body = new RequestBody();
    private URL url = null;
    private final Map<String, String> pathParams = new HashMap<>();
    private Method method = Method.GET;

    public RequestBody getBody() {
        return body;
    }

    public URL getUrl() {
        try {
            URI uri = url.toURI();
            StringBuilder query = new StringBuilder(uri.getQuery() == null ? "" : uri.getQuery());
            for (Map.Entry entry : pathParams.entrySet()) {
                if (query.length() == 0) {
                    query = new StringBuilder(entry.getKey() + "=" + entry.getValue());
                } else {
                    query.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                }
            }
            return new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(),
                    uri.getPath(), query.toString(), uri.getFragment()).toURL();
        } catch (Exception e) {
            throw new RuntimeException("Wrong url");
        }
    }

    public String getMethod() {
        return method.name();
    }

    public enum Method {
        GET,
        POST
    }

    public static class Builder {
        private Request request = new Request();

        public Builder() {

        }

        public Builder(Request request) {
            this.request.method = request.method;
            this.request.body = request.body.copy();
            this.request.url = request.url;
        }

        public Builder setUrl(String url) throws RuntimeException {
            try {
                request.url = new URL(url);
            } catch (MalformedURLException e) {
                throw new RuntimeException("URL is not correct");
            }
            return this;
        }

        public Builder setBody(RequestBody body) {
            request.body = body;
            return this;
        }

        public Builder setMethod(Method method) {
            request.method = method;
            return this;
        }

        public Request build() throws RuntimeException {
            if (request.url == null) throw new RuntimeException("Path was not specified");
            return request;
        }

        public Builder setPathParams(@Nullable Map<String,String> pathParams) {
            if (pathParams == null) {
                request.pathParams.clear();
            } else {
                request.pathParams.putAll(pathParams);
            }
            return this;
        }
    }

}
