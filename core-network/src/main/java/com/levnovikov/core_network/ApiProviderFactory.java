package com.levnovikov.core_network;

import com.levnovikov.core_network.api_provider.ApiProvider;
import com.levnovikov.core_network.converter.GsonConverter;
import com.levnovikov.core_network.di.NetworkModule;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

public class ApiProviderFactory {

    private final HttpClient client;
    private final String baseUrl;

    @Inject
    public ApiProviderFactory(
            @Named(NetworkModule.NETWORK_CLIENT) HttpClient client,
            @Named(NetworkModule.BASE_URL) String baseUrl) {
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
