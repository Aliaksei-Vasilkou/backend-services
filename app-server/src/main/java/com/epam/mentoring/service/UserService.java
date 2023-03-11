package com.epam.mentoring.service;

import com.epam.mentoring.exception.UserNotFoundException;
import com.epam.mentoring.domain.model.UserDto;
import com.epam.mentoring.repository.UserRepository;
import com.epam.mentoring.utils.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserMapper mapper;

    public UserDto getById(Long id) {
        return mapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found by id=" + id)));
    }
}
