package com.levnovikov.core_network;

import com.levnovikov.core_network.request.Request;
import com.levnovikov.core_network.request.RequestTransformer;
import com.levnovikov.core_network.response.Response;
import com.levnovikov.core_network.response.ResponseTransformer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleTransformer;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

public class HttpClientImpl implements HttpClient {

    private final List<RequestTransformer> requestTransformers = new ArrayList<>();
    private final List<ResponseTransformer> responseTransformers = new ArrayList<>();
    private CallExecutor callExecutor;
    private SingleTransformer<Response, Response> threadComposer;

    private HttpClientImpl() {}

    @Override
    public void addTransformer(RequestTransformer transformer) {
        requestTransformers.add(transformer);
    }

    @Override
    public void addTransformer(ResponseTransformer transformer) {
        responseTransformers.add(transformer);
    }

    @Override
    public Single<Response> makeCall(Request request) {
        return Single.create((SingleOnSubscribe<Response>) emitter -> emitter.onSuccess(
                applyResponseTransformers(
                        callExecutor.execute(
                                applyRequestTransformers(request)))))
                .compose(threadComposer);
    }


    private Request applyRequestTransformers(Request request) {
        for (RequestTransformer transformer : requestTransformers) {
            request = transformer.transform(request);
        }
        return request;
    }

    private Response applyResponseTransformers(Response response) {
        for (ResponseTransformer transformer : responseTransformers) {
            response = transformer.transform(response);
        }
        return response;
    }

    public static class Builder {
        private final HttpClientImpl client = new HttpClientImpl();

        public Builder() {
            client.callExecutor = new UrlConnectionCallExecutor();
            client.threadComposer = new StandardThreadComposer();
        }

        public Builder threadComposer(SingleTransformer<Response, Response> threadComposer) {
            client.threadComposer = threadComposer;
            return this;
        }

        public Builder callExecutor(CallExecutor callExecutor) {
            client.callExecutor = callExecutor;
            return this;
        }

        public HttpClient build() {
            return client;
        }
    }
}
