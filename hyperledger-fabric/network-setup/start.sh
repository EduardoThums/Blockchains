#!/bin/bash

CHANNEL_NAME=mychannel

# Import crypto-config util 
. scripts/crypto-util.sh

#Stop and remove previous containers
echo -e "\nRemove previous networks"
docker-compose down --volumes --remove-orphan

# Generate general crypto config
generateCerts
generateChannelArtifacts

# Export private ca key
# exportPrivateCaKey

#Start network
docker-compose up -d

docker exec cli scripts/channel-util.sh $CHANNEL_NAME