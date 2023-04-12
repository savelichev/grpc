package com.axarit.grpc.client;

import com.axarit.grpc.HelloResponse;
import com.google.common.util.concurrent.FutureCallback;

public class StudentFutureCallback implements FutureCallback<HelloResponse> {

    @Override
    public void onSuccess(HelloResponse result) {
        System.out.println(Thread.currentThread().getName() + " : " + result.getGreeting());
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
