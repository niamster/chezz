#/usr/bin/env bash

./gradlew bootRun --args='--spring.config.location=file:./config/configuration.yaml' $@