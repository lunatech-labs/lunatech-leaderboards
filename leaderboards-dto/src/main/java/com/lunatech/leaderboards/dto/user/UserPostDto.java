package com.lunatech.leaderboards.dto.user;

import javax.validation.constraints.NotBlank;

public record UserPostDto(
        @NotBlank String email,
        @NotBlank String displayName,
        String profilePicUrl) {
}
