#!/bin/bash

while ! exec 6<>/dev/tcp/$DOCKER_HOST_IP/27017; do
    echo "Trying to connect to MongoDB at 27017..."
    sleep 10
done

while ! exec 6<>/dev/tcp/$DOCKER_HOST_IP/3306; do
    echo "Trying to connect to MySQL at 3306..."
    sleep 10
done

