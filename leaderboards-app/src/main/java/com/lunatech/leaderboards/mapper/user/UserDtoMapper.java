package com.lunatech.leaderboards.mapper.user;

import com.lunatech.leaderboards.dto.user.UserDto;
import com.lunatech.leaderboards.dto.user.UserPostDto;
import com.lunatech.leaderboards.entity.User;
import com.lunatech.leaderboards.mapper.DtoMapper;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserDtoMapper implements DtoMapper<User, UserPostDto, UserDto> {
    @Override
    public User toEntity(UserPostDto dto) {
        User user = new User();
        user.email = dto.email();
        user.displayName = dto.displayName();
        user.profilePicUrl = dto.profilePicUrl();
        return user;
    }

    @Override
    public UserDto toDto(User user) {
        return new UserDto(user.id, user.email, user.displayName, user.profilePicUrl);
    }
}
