package com.epam.mentoring.service;

import com.epam.mentoring.domain.model.UserDto;
import com.epam.mentoring.grpc.UserRegistration.UserRegistrationRequest;
import com.epam.mentoring.grpc.UserRegistrationServiceGrpc;
import com.epam.mentoring.grpc.UserRegistrationServiceGrpc.UserRegistrationServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class GrpcNotificationService {

    private final UserRegistrationServiceBlockingStub stub;

    public GrpcNotificationService() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8100)
                .usePlaintext()
                .build();
        this.stub = UserRegistrationServiceGrpc.newBlockingStub(channel);
    }

    public void sendNotification(UserDto userDto) {
        log.info("Sending registration notification for {}", userDto);
        UserRegistrationRequest request = UserRegistrationRequest.newBuilder()
                .setFirstName(userDto.getFirstName())
                .setLastName(userDto.getLastName())
                .setEmail(userDto.getEmail())
                .setTimestamp(System.currentTimeMillis())
                .build();

        stub.sendNotification(request);
        log.info("Notification successfully sent for {}", userDto);
    }
}
