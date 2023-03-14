package com.epam.mentoring;

import com.epam.mentoring.grpc.UserRegistrationRequest;
import com.epam.mentoring.grpc.UserRegistrationResponse;
import com.epam.mentoring.grpc.UserRegistrationServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRegistrationClient extends UserRegistrationServiceGrpc.UserRegistrationServiceImplBase {

    private static final Logger LOGGER = Logger.getLogger(UserRegistrationClient.class.getName());

    @Override
    public void sendNotification(UserRegistrationRequest request, StreamObserver<UserRegistrationResponse> responseObserver) {
        String message = MessageFormat.format("Notification sent for {0} {1} on email address {2}",
                request.getFirstName(), request.getLastName(), request.getEmail());
        UserRegistrationResponse response = UserRegistrationResponse.newBuilder()
                .setMessage(message)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
        LOGGER.log(Level.INFO, "Client response: {}", response);
    }
}
