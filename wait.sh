#!/bin/bash

while ! exec 6<>/dev/tcp/192.168.1.56/27017; do
    echo "Trying to connect to MongoDB at 27017..."
    sleep 10
done

while ! exec 6<>/dev/tcp/192.168.1.56/3306; do
    echo "Trying to connect to MySQL at 3306..."
    sleep 10
done

