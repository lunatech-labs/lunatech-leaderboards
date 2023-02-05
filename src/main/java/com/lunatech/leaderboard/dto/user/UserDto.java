package com.lunatech.leaderboard.dto.user;

import com.lunatech.leaderboard.entity.User;

public record UserDto(Long id, String email, String displayName, String profilePicUrl) {
}
