package com.levnovikov.core_network.response;

/**
 * Created by lev.novikov
 * Date: 27/3/18.
 */

public interface ResponseTransformer {
    Response transform(Response response);
}
