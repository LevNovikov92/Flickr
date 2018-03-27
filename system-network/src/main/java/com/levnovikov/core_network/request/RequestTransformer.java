package com.levnovikov.core_network.request;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

public interface RequestTransformer {
    Request transform(Request request);
}
