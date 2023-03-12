package com.epam.mentoring;

import com.epam.mentoring.grpc.UserRegistration.UserRegistrationRequest;
import com.epam.mentoring.grpc.UserRegistration.UserRegistrationResponse;
import com.epam.mentoring.grpc.UserRegistrationServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.text.MessageFormat;

public class UserRegistrationClient extends UserRegistrationServiceGrpc.UserRegistrationServiceImplBase {

    @Override
    public void sendNotification(UserRegistrationRequest request, StreamObserver<UserRegistrationResponse> responseObserver) {
        String message = MessageFormat.format("Notification sent for {0} {1} on email address {2}",
                request.getFirstName(), request.getLastName(), request.getEmail());
        UserRegistrationResponse response = UserRegistrationResponse.newBuilder()
                .setMessage(message)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
        System.out.println("Client response: " + response);
    }
}
