#!/bin/bash

# Import util scrips
. scripts/clean-util.sh

# Remove old kafka network
echo "Remove old kafka network"
removeContainers