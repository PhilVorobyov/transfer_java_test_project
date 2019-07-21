#!/usr/bin/env bash

JAR="./build/libs/transfer-1.0-SNAPSHOT.jar"

# First remove all previously migrated data
echo "running migrate clean"
gradle flywayClean -i;

# Migrate all data
echo "migrate all data"
gradle flywayMigrate -i;

# Check that jar is already built
if test -f "$JAR"; then
    echo "$JAR is already built"
    else
    echo "package jar file"; gradle jar;
fi

# Run application
echo "run application"
java -jar $JAR


