#!/bin/bash

VIDEO_IPFS_TOPIC=video-ipfs-topic
IPFS_FABRIC_TOPIC=ipfs-fabric-topic

. scripts/clean-util.sh
. scripts/topic-util.sh

removeContainers

docker-compose up -d

createTopic $VIDEO_IPFS_TOPIC
createTopic $IPFS_FABRIC_TOPIC
