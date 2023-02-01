package com.axarit.grpc.client;

import com.axarit.grpc.grpc.HelloRequest;
import com.axarit.grpc.grpc.HelloResponse;
import com.axarit.grpc.grpc.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);

        HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                .setFirstName("Axarit")
                .setLastName("gRPC")
                .build());

        System.out.println(helloResponse.getGreeting());

        channel.shutdown();
    }
}
