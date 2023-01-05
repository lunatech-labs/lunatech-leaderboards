package com.lunatech.leaderboards.dto.user;

import com.lunatech.leaderboards.entity.User;

public record UserDto(Long id, String email, String displayName, String profilePicUrl) {
    public UserDto(User user) {
        this(user.id, user.email, user.displayName, user.profilePicUrl);
    }

    public User toEntity() {
        User user = new User();
        user.id = id;
        user.email = email;
        user.displayName = displayName;
        user.profilePicUrl = profilePicUrl;
        return user;
    }
}
