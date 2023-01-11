# CONTRIBUTING

## Running the application
1. Run `docker-compose -f .\docker-compose-dev.yml up` to start the DB
2. Run `cd ./leaderboards && ./mvnw quarkus:dev`

### Available Keycloak Users
* User - username: user, password: user, no roles
* Admin - username: admin, password: admin, admin role
* No User - username: nouser, password: nouser, not signed into the app
