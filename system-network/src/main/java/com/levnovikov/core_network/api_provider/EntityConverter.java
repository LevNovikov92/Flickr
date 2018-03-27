package com.levnovikov.core_network.api_provider;

/**
 * Created by lev.novikov
 * Date: 23/2/18.
 */

public interface EntityConverter {
    <T> String convertTo(T entity);
    <T> T convertFrom(String string, Class<T> responseClass);
}
