# Leaderboards

Run keycloak on docker  
`docker run --rm -it -p 8081:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -v $(pwd)/leaderboards/src/main/resources/quarkus-realm.json:/opt/keycloak/data/import/quarkus-realm.json quay.io/keycloak/keycloak:19.0.3 start-dev --import-realm`

`kc.sh --dir /export --users realm_file --realm Quarkus`