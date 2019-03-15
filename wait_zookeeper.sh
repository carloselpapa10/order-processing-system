#!/bin/bash

while ! exec 6<>/dev/tcp/$DOCKER_HOST_IP/2181; do
    echo "Trying to connect to Zookeeper at 2181..."
    sleep 10
done





