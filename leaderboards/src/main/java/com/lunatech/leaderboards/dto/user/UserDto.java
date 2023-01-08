package com.lunatech.leaderboards.dto.user;

import com.lunatech.leaderboards.entity.User;

public record UserDto(Long id, String email, String displayName, String profilePicUrl) {
    public UserDto(User user) {
        this(user.id, user.email, user.displayName, user.profilePicUrl);
    }
}
