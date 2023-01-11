BASEDIR=$(dirname "$0")

docker exec -u root keycloak_temp_instance /opt/keycloak/bin/kc.sh export --dir / --users realm_file --realm Quarkus
docker cp keycloak_temp_instance:/Quarkus-realm.json $BASEDIR/../../leaderboards/src/main/resources/quarkus-realm.json