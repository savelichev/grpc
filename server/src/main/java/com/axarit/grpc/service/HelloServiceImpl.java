package com.axarit.grpc.service;

import com.axarit.grpc.grpc.HelloRequest;
import com.axarit.grpc.grpc.HelloResponse;
import com.axarit.grpc.grpc.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {

        String greeting = "Hello, " + request.getFirstName() + " " + request.getLastName();

        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting(greeting)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
