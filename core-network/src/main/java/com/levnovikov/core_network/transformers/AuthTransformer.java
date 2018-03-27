package com.levnovikov.core_network.transformers;

import com.levnovikov.core_network.request.Request;
import com.levnovikov.core_network.request.RequestTransformer;

import javax.inject.Inject;
import javax.inject.Named;

import static com.levnovikov.core_network.di.NetworkModule.API_KEY;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

public class AuthTransformer implements RequestTransformer {

    private final static String API_KEY_NAME = "api_key";

    private final String apiKey;

    @Inject
    public AuthTransformer(@Named(API_KEY) String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Request transform(Request request) {
        return new Request.Builder(request)
                .addPathParam(API_KEY_NAME, apiKey).build();
    }
}
