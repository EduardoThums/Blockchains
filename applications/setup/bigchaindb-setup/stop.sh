#!/bin/bash

# Import util scrips
. scripts/clean-util.sh

# Remove old bigchaindb network
echo "Remove old bigchaindb network"
removeContainers