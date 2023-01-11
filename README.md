# Leaderboards

Application built to offer a system to track wins, losses and overall player score for games played within Lunatech

## Description

The app is divided in the following sections:
* [Games](#games)
* [Game Modes](#game-modes)
* [Matches](#matches)
* [Users](#users)

### Games
Games describe which game is being played (e.g. Pool, Foosball, etc.)  
This is a macro-category that holds one or more [Game Modes](#game-modes) and its only purpose is the group them up.

### Game Modes
Game Modes are the core the of the application. They are tied to a [Game](#games) and specify a ruleset which needs to be followed.  
The Game Mode will show various information, like its related [Leaderboard](#game-mode---leaderboard) and the [Matches](#matches) that have been played. 

#### Game Mode - Leaderboard
This shows a ranking of the users who played the [Game Mode](#game-modes), its values are updated whenever a [Match](#matches) is confirmed by all the [Users](#users) involved. The score gain/loss will be determined using the [Elo algorithm](https://en.wikipedia.org/wiki/Elo_rating_system)

### Matches
Matches are created when 2 teams face against each other under one [Game Mode](#game-modes). The Match will need the following information:
* Team A users
* Team B users
* Outcome

Once a match is created, every user will have to confirm its outcome for the [Leaderboard](#game-mode---leaderboard) to be updated

### Users
Users are generally picked from Keycloak. Upon first calling one of the endpoints for the app, the user will be saved on the db in order to store relations to [Game Modes](#game-modes) and [Matches](#matches)