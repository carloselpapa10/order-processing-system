#!/bin/bash

while ! exec 6<>/dev/tcp/$DOCKER_HOST_IP/9092; do
    echo "Trying to connect to Kafka at 9092..."
    sleep 10
done





