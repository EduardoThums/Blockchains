#!/bin/bash

CHANNEL_NAME=mychannel
SYS_CHANNEL=byfn-sys-channel

# Import crypto-util functions 
. scripts/crypto-util.sh

# Import clean-util functions
. scripts/clean-util.sh

#Stop and remove previous containers
echo -e "Remove previous networks"
removeContainers

# Generate general crypto config
generateCerts
generateChannelArtifacts

# Export private ca key
exportPrivateCaKey

#Start network
docker-compose up -d

docker exec cli scripts/channel-util.sh $CHANNEL_NAME