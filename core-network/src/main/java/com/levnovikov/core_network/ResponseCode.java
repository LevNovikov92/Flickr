package com.levnovikov.core_network;

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

public enum ResponseCode {
    SUCCESS(200);

    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
