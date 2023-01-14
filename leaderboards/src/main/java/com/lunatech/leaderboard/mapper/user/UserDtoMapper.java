package com.lunatech.leaderboard.mapper.user;

import com.lunatech.leaderboard.dto.user.UserPostDto;
import com.lunatech.leaderboard.entity.User;
import com.lunatech.leaderboard.mapper.DtoMapper;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserDtoMapper implements DtoMapper<UserPostDto, User> {
    @Override
    public User toEntity(UserPostDto dto) {
        User user = new User();
        user.email = dto.email();
        user.displayName = dto.displayName();
        user.profilePicUrl = dto.profilePicUrl();
        return user;
    }
}
