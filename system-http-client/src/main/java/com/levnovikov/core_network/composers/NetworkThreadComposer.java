package com.levnovikov.core_network.composers;

import com.levnovikov.core_network.response.Response;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lev.novikov
 * Date: 27/3/18.
 */

public class NetworkThreadComposer implements SingleTransformer<Response, Response> {

    @Override
    public SingleSource<Response> apply(Single<Response> upstream) {
        return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
