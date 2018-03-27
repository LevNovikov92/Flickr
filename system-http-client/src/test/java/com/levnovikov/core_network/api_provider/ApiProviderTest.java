package com.levnovikov.core_network.api_provider;

import com.google.gson.Gson;
import com.levnovikov.core_network.HttpClient;
import com.levnovikov.core_network.request.Request;
import com.levnovikov.core_network.response.Response;
import com.levnovikov.core_network.response.ResponseBody;

import junit.framework.Assert;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */
public class ApiProviderTest {

    class UserDataRequest {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
    class UserDataResponse {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    @Test
    public void makeRequest() throws Exception {
        EntityConverter converter = new EntityConverter() {

            private Gson gson = new Gson();

            @Override
            public <T> String convertTo(T entity) {
                return gson.toJson(entity);
            }

            @Override
            public <T> T convertFrom(String string, Class<T> responseClass) {
                return gson.fromJson(string, responseClass);
            }
        };

        HttpClient client = request -> {
            UserDataResponse response = new UserDataResponse();
            response.age = 20;
            response.name = "User name";
            ResponseBody responseBody = new ResponseBody(
                    new ByteArrayInputStream(converter.convertTo(response).getBytes(StandardCharsets.UTF_8)), "utf-8", "");
            return Single.just(new Response.Builder().body(responseBody).build());
        };

        ApiProvider provider = new ApiProvider.Builder()
                .baseUrl("https://google.com")
                .client(client)
                .converter(converter)
                .build();

        UserDataRequest request = new UserDataRequest();
        request.id = "5";
        TestObserver<UserDataResponse> observer = provider
                .makeRequest(Request.Method.POST, "/sdfsd/", request, UserDataResponse.class).test();
        observer.assertValueCount(1);
        UserDataResponse response = observer.values().get(0);
        Assert.assertEquals(20, response.age);
        Assert.assertEquals("User name", response.name);
    }

}