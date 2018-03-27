package com.levnovikov.core_network.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.levnovikov.core_network.api_provider.EntityConverter;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

public class GsonConverter implements EntityConverter {

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    @Override
    public <T> String convertTo(T entity) {
        return gson.toJson(entity);
    }

    @Override
    public <T> T convertFrom(String string, Class<T> responseClass) {
        return gson.fromJson(string, responseClass);
    }
}
