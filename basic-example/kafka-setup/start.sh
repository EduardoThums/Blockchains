#!/bin/bash

# Constants
TOPIC_NAME=producer-topic

# Import util scrips
. scripts/clean-util.sh
. scripts/topic-util.sh

# Remove old kafka network
removeContainers

# Builsk new kafka network
docker-compose up -d

sleep 10

# Create producer topic
createTopic