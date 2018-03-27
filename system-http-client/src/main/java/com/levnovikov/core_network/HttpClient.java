package com.levnovikov.core_network;

import com.levnovikov.core_network.request.Request;
import com.levnovikov.core_network.request.RequestTransformer;
import com.levnovikov.core_network.response.Response;
import com.levnovikov.core_network.response.ResponseTransformer;

import io.reactivex.Single;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

public interface HttpClient {

    Single<Response> makeCall(Request request);
}
