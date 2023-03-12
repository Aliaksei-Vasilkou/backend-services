package com.epam.mentoring.service;

import com.epam.mentoring.domain.entity.User;
import com.epam.mentoring.exception.UserNotFoundException;
import com.epam.mentoring.domain.model.UserDto;
import com.epam.mentoring.repository.UserRepository;
import com.epam.mentoring.utils.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserMapper mapper;
    private GrpcNotificationService notificationService;

    public UserDto save(UserDto userDto) {
        log.info("User registration request retrieved {}", userDto);
        userRepository.save(mapper.toEntity(userDto));
        notificationService.sendNotification(userDto);

        return userDto;
    }

    public UserDto getById(Long id) {
        log.info("Searching user by id {}", id);

        return mapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found by id=" + id)));
    }
}
