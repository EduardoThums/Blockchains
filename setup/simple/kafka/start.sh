#!/bin/bash

VIDEO_DISTRIBUTED_TOPIC=video-distributed-storage-topic
DISTRIBUTED_STORAGE_BLOCKCHAIN_TOPIC=distributed-storage-blockchain-topic
BLOCKCHAIN_LOGGER_TOPIC=blockchain-logger-topic

. scripts/clean-util.sh
. scripts/topic-util.sh

removeContainers

docker-compose up -d

createTopic $VIDEO_DISTRIBUTED_TOPIC
createTopic $DISTRIBUTED_STORAGE_BLOCKCHAIN_TOPIC
createTopic $BLOCKCHAIN_LOGGER_TOPIC
