WITH gameid AS (INSERT INTO game (name) VALUES ('Table Tennis') RETURNING id),
     gamemodes as (SELECT gamemode.column1 as name, gamemode.column2 as rules
                   FROM (VALUES ('Duel', '1v1 only, up to 11/21 points'),
                                ('Pairs', '2v2 only, up to 11/21 points, 1 hit each player')) gamemode)
INSERT INTO game_mode(name, rules, game)
SELECT gamemodes.name, gamemodes.rules, gameid.id
FROM gameid, gamemodes;

INSERT INTO app_user(email, displayname)
VALUES ('testuser1@email.nl', 'Test User 1'), ('testuser2@email.nl', 'Test User 2');