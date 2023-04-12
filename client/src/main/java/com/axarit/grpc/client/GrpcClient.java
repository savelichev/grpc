package com.axarit.grpc.client;

import com.axarit.grpc.HelloRequest;
import com.axarit.grpc.HelloResponse;
import com.axarit.grpc.HelloServiceGrpc;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GrpcClient {

    public static void main(String[] args) {
        var executorService = Executors.newFixedThreadPool(10);
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .executor(executorService)
                .usePlaintext()
                .build();

        asyncStub(channel);
        blockingStub(channel);
        futureStub(channel);

        channel.shutdown();
    }

    private static void asyncStub(ManagedChannel channel) {
        final HelloServiceGrpc.HelloServiceStub stub = HelloServiceGrpc.newStub(channel);
        final HelloRequest request = HelloRequest.newBuilder()
                .setFirstName("Axarit")
                .setLastName("gRPC - asyncStub")
                .build();

        stub.hello(request, new StudentCallback());
    }

    private static void blockingStub(ManagedChannel channel) {
        HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);

        HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                .setFirstName("Axarit")
                .setLastName("gRPC - blockingStub")
                .build());
        System.out.println(Thread.currentThread().getName() + " : " + helloResponse.getGreeting());
    }

    private static void futureStub(ManagedChannel channel) {
        final HelloServiceGrpc.HelloServiceFutureStub stub = HelloServiceGrpc.newFutureStub(channel);
        final HelloRequest request = HelloRequest.newBuilder()
                .setFirstName("Axarit")
                .setLastName("gRPC - futureStub")
                .build();
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final ListenableFuture<HelloResponse> listenableFuture = stub.hello(request);
        Futures.addCallback(listenableFuture, new StudentFutureCallback(), executorService);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
