package com.axarit.grpc.client;

import com.axarit.grpc.HelloRequest;
import com.axarit.grpc.HelloResponse;
import com.axarit.grpc.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class GrpcClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        asyncStub(channel);
        blockingStub(channel);

        channel.shutdown();

    }

    private static void asyncStub(ManagedChannel channel) {
        final HelloServiceGrpc.HelloServiceStub stub = HelloServiceGrpc.newStub(channel);
        final HelloRequest request = HelloRequest.newBuilder()
                .setFirstName("Axarit")
                .setLastName("gRPC - asyncStub")
                .build();

        stub.hello(
                request,
                new StreamObserver<>() { //Callback
                    @Override
                    public void onNext(HelloResponse value) {
                        System.out.println(value.getGreeting());
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("axa onCompleted");
                    }
                }
        );

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void blockingStub(ManagedChannel channel) {
        HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);

        HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                .setFirstName("Axarit")
                .setLastName("gRPC - blockingStub")
                .build());

        System.out.println(helloResponse.getGreeting());
    }
}
