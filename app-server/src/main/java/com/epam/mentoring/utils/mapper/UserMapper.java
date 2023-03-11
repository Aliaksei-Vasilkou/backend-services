package com.epam.mentoring.utils.mapper;

import com.epam.mentoring.domain.entity.User;
import com.epam.mentoring.domain.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User entity);

    User toEntity(UserDto entity);
}
