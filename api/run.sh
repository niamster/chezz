#/usr/bin/env bash

if ! ./gradlew verGJF; then
  echo "==========================="
  echo "run './gradlew goJF' to fix"
  echo "==========================="
  exit 1
fi
./gradlew bootRun --args='--spring.config.location=file:./config/configuration.yaml' $@
