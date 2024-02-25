package org.phonetesting.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.phonetesting.dtos.UserDTO;
import org.phonetesting.persistence.User;


@Mapper
public interface UserMapper {
    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO toUserDTO(User user);
}
