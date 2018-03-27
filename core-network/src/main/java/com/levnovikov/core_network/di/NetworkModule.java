package com.levnovikov.core_network.di;

import com.levnovikov.core_network.HttpClient;
import com.levnovikov.core_network.HttpClientFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

@Module
public class NetworkModule {

    public static final String API_KEY = "API_KEY";
    public static final String NETWORK_CLIENT = "NETWORK_CLIENT";
    public static final String IMAGE_LOADER_CLIENT = "IMAGE_LOADER_CLIENT";
    public static final String BASE_URL = "BASE_URL";

    @Provides
    @Named(API_KEY)
    String providesApiKey() { //TODO move to app module
        return "fe1d3418135ddd69c31f5630b8c521e3";
    }

    @Provides
    @Named(BASE_URL)
    String providesBaseUrl() { //TODO move to app module
        return "https://api.flickr.com";
    }

    @Provides
    @Singleton
    @Named(NETWORK_CLIENT)
    HttpClient provideNetworkClient(HttpClientFactory factory) {
        return factory.buildNetworkClient();
    }

    @Provides
    @Singleton
    @Named(IMAGE_LOADER_CLIENT)
    HttpClient provideImageLoaderClient(HttpClientFactory factory) {
        return factory.buildImageLoaderClient();
    }
}
