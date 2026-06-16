package com.caroarias.tarea1.auth.mappers;

import com.caroarias.tarea1.auth.models.dtos.UserDTO;
import com.caroarias.tarea1.auth.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "role.name", target = "role")
    UserDTO toDto(User user);
}
