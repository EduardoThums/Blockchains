#!/bin/bash

# Import util scrips
. scripts/clean-util.sh

# Remove old kafka network
removeContainers

# Builsk new kafka network
docker-compose up -d