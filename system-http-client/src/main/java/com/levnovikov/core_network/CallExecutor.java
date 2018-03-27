package com.levnovikov.core_network;

import com.levnovikov.core_network.request.Request;
import com.levnovikov.core_network.response.Response;

import java.io.IOException;

/**
 * Created by lev.novikov
 * Date: 27/3/18.
 */

public interface CallExecutor {
    Response execute(Request request) throws IOException;
}
