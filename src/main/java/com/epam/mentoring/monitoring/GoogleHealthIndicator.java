package com.epam.mentoring.monitoring;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
@Endpoint(id = "googleHealth")
@AllArgsConstructor
public class GoogleHealthIndicator {

    public static final Random RANDOM = new Random();

    @ReadOperation
    public Map<String, String> checkStatus() {
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("www.google.com", RANDOM.nextBoolean() ? "UP" : "DOWN");

        return statusMap;
    }
}
