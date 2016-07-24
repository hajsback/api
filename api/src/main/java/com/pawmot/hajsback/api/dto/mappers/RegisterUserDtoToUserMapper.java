package com.pawmot.hajsback.api.dto.mappers;

import com.pawmot.hajsback.api.dto.RegisterUserDto;
import com.pawmot.hajsback.api.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface RegisterUserDtoToUserMapper extends com.pawmot.hajsback.api.dto.mappers.Mapper<RegisterUserDto, User> {
    @Override
    User map(RegisterUserDto registerUserDto);
}
