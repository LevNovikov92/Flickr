package com.levnovikov.core_network.response;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

public class Response {

    private int code;
    private ResponseBody body;

    private Response() {

    }

    public int getCode() {
        return code;
    }

    public ResponseBody getBody() {
        return body;
    }

    public static class Builder {
        private final Response response = new Response();

        public Builder code(int code) {
            response.code = code;
            return this;
        }

        public Builder body(ResponseBody body) {
            response.body = body;
            return this;
        }

        public Response build() {
            return response;
        }
    }
}
