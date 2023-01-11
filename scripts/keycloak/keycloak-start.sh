BASEDIR=$(dirname "$0")

docker run --rm -it -p 8081:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin \
  -v $BASEDIR/../../leaderboards/src/main/resources/quarkus-realm.json:/opt/keycloak/data/import/quarkus-realm.json \
  --name keycloak_temp_instance \
  quay.io/keycloak/keycloak:19.0.3 start-dev --import-realm