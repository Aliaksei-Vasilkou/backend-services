package com.epam.mentoring.service;

import com.epam.mentoring.exception.UserNotFoundException;
import com.epam.mentoring.domain.model.UserDto;
import com.epam.mentoring.repository.UserRepository;
import com.epam.mentoring.utils.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserMapper mapper;
    private GrpcNotificationService grpcNotificationService;
    private KafkaNotificationService kafkaNotificationService;

    public UserDto save(UserDto userDto) {
        log.info("User registration request retrieved {}", userDto);
        userRepository.save(mapper.toEntity(userDto));
        sendNotification(userDto);

        return userDto;
    }

    public UserDto getById(Long id) {
        log.info("Searching user by id {}", id);

        return mapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found by id=" + id)));
    }

    private void sendNotification(UserDto userDto) {
        grpcNotificationService.sendNotification(userDto);
        kafkaNotificationService.sendNotification(userDto);
    }
}
