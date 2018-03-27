package com.levnovikov.core_network.request;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

public class RequestBody {

    private String content = "";
    private String contentType = "";

    public String getContent() {
        return content;
    }

    public RequestBody() {}

    public RequestBody(String content, String contentType) {
        this.content = content;
        this.contentType = contentType;
    }

    public void setContent(String content, String contentType) {
        this.content = content;
        this.contentType = contentType;
    }

    public boolean isNotEmpty() {
        return !content.isEmpty();
    }

    public RequestBody copy() {
        RequestBody body = new RequestBody();
        body.content = content;
        body.contentType = contentType;
        return body;
    }
}
