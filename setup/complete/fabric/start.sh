#!/bin/bash

CHANNEL_NAME=mychannel
SYS_CHANNEL=byfn-sys-channel

. scripts/crypto-util.sh
. scripts/clean-util.sh

echo -e "Remove previous networks"
removeContainers

generateCerts
generateChannelArtifacts
exportPrivateCaKey

docker-compose up -d

docker exec cli scripts/channel-util.sh $CHANNEL_NAME