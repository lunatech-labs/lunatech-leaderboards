package com.lunatech.leaderboard.dto.user;

import com.lunatech.leaderboard.entity.User;

public record UserPostDto(String displayName, String profilePicUrl) {

    public User toEntity(String email) {
        User user = new User();
        user.email = email;
        user.displayName = displayName;
        user.profilePicUrl = profilePicUrl;
        return user;
    }
}
