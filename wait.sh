#!/bin/bash

while ! exec 6<>/dev/tcp/localhost/3306; do
    echo "Trying to connect to MySQL at 3306..."
    sleep 10
done
