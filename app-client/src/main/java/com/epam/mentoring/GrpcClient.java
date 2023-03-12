package com.epam.mentoring;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcClient {

    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(8100)
                .addService(new UserRegistrationClient())
                .build();

        server.start();
        server.awaitTermination();
    }
}
