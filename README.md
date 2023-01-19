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

# leaderboards

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.
## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/leaderboards-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.
