package com.lunatech.leaderboard.dto.user;

import javax.validation.constraints.NotBlank;

public record UserPostDto(
        @NotBlank String email,
        @NotBlank String displayName,
        String profilePicUrl) {
}
