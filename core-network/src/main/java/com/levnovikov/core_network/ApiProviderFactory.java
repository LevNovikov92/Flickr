package com.levnovikov.core_network;

import com.levnovikov.core_network.api_provider.ApiProvider;
import com.levnovikov.core_network.converter.GsonConverter;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

public class ApiProviderFactory {

    private final HttpClient client;
    private final String baseUrl;

    public ApiProviderFactory(
            HttpClient client,
            String baseUrl) {
        this.client = client;
        this.baseUrl = baseUrl;
    }

    public ApiProvider buildApiProvider() {
        return new ApiProvider.Builder()
                .baseUrl(baseUrl)
                .client(client)
//                .contentType() TODO implement
                .converter(new GsonConverter())
                .build();
    }
}
