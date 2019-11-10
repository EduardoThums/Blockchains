#!/bin/bash

DISTRIBUTED_STORAGE_BLOCKCHAIN_TOPIC=distributed-storage-blockchain-topic
BLOCKCHAIN_LOGGER_TOPIC=blockchain-logger-topic

. scripts/clean-util.sh
. scripts/topic-util.sh

removeContainers

docker-compose up -d

createTopic $DISTRIBUTED_STORAGE_BLOCKCHAIN_TOPIC
createTopic $BLOCKCHAIN_LOGGER_TOPIC