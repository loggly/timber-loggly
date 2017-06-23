#!/bin/bash -e

./gradlew assembleRelease
cp -vf ./timber-loggly/build/outputs/aar/timber-loggly*.aar build/timber-loggly.aar
cp -vf ./timber-loggly/build/intermediates/bundles/release/classes.jar build/timber-loggly.jar
