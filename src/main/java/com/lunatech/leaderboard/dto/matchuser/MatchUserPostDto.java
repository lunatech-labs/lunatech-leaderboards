package com.lunatech.leaderboard.dto.matchuser;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record MatchUserPostDto(@Email @NotBlank String userEmail) {
}
