package com.epam.mentoring;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.epam.mentoring.common.utils.GrpcConstants.SERVER_PORT;

public class GrpcClient {

    private static final Logger LOGGER = Logger.getLogger(GrpcClient.class.getName());

    public static void main(String[] args) {
        Server server = ServerBuilder.forPort(SERVER_PORT)
                .addService(new UserRegistrationClient())
                .build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.log(Level.INFO, "Closing connection");
            server.shutdown();
        }));

        try {
            server.start();
            LOGGER.log(Level.INFO, "gRPC client has started");
            server.awaitTermination();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
