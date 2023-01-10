package com.lunatech.leaderboards.dto.user;

import com.lunatech.leaderboards.entity.User;

public record UserPostDto(String displayName, String profilePicUrl) {

    public User toEntity(String email) {
        User user = new User();
        user.email = email;
        user.displayName = displayName;
        user.profilePicUrl = profilePicUrl;
        return user;
    }
}
