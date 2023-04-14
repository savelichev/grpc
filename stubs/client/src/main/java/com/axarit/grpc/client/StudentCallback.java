package com.axarit.grpc.client;

import com.axarit.grpc.HelloResponse;
import io.grpc.stub.StreamObserver;

public class StudentCallback implements StreamObserver<HelloResponse> {

    @Override
    public void onNext(HelloResponse value) {
        System.out.println(Thread.currentThread().getName() + " : " + value.getGreeting());
    }

    @Override
    public void onError(Throwable t) {
    }

    @Override
    public void onCompleted() {
        System.out.println("axa onCompleted");
    }
}
