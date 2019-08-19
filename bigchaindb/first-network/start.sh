#!/bin/bash

# Import util scrips
. scripts/clean-util.sh

# Remove old bigchaindb network
removeContainers

# Remove old volumes
# removeVolumes

# Build new bigchaindb network
docker-compose up -d