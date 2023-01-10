CREATE TABLE app_user (
    id BIGSERIAL PRIMARY KEY,
    email TEXT NOT NULL UNIQUE,
    displayName TEXT UNIQUE,
    profilePicUrl TEXT
);

CREATE TABLE game (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    image_url TEXT
);

CREATE TABLE game_mode (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    rules TEXT,
    game BIGINT NOT NULL REFERENCES game
);

CREATE TYPE match_outcome AS ENUM ('TEAM_A', 'TEAM_B', 'DRAW', 'CANCELLED', 'ONGOING');

CREATE TYPE match_team AS ENUM ('TEAM_A', 'TEAM_B');

CREATE TABLE match (
    id BIGSERIAL PRIMARY KEY,
    outcome match_outcome NOT NULL,
    game_mode BIGINT NOT NULL REFERENCES game_mode,
    confirmed BOOLEAN DEFAULT false
);

CREATE TABLE match_user (
    app_user BIGINT NOT NULL REFERENCES app_user,
    match BIGINT NOT NULL REFERENCES match,
    team match_team NULL,
    outcome_confirmed BOOLEAN DEFAULT false,
    PRIMARY KEY (app_user, match)
);

CREATE TABLE leaderboard_user (
    app_user BIGINT NOT NULL REFERENCES app_user,
    game_mode BIGINT NOT NULL REFERENCES game_mode,
    score INT NOT NULL,
    growth_factor INT NOT NULL,
    PRIMARY KEY (app_user, game_mode)
);