#!/bin/bash

# Import util scrips
. scripts/clean-util.sh

# Remove old bigchaindb network
removeContainers

# Build new bigchaindb network
docker-compose up -d