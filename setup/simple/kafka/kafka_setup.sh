#!/bin/bash

stop_kafka() {
    docker-compose -f kafka/docker-compose.yaml down --volumes --remove-orphan
}

create_topic() {
    TOPIC_NAME=$1

    docker exec -it kafka0 kafka-topics --create --topic $TOPIC_NAME --partitions 1 --replication-factor 1 --if-not-exists --zookeeper zookeeper0:2181 >&log.txt
    res=$?

    while [ $res -ne 0 ]; do
        docker exec -it kafka0 kafka-topics --create --topic $TOPIC_NAME --partitions 1 --replication-factor 1 --if-not-exists --zookeeper zookeeper0:2181 >&log.txt
        res=$?
    done

    rm log.txt
}

start_kafka() {
    VIDEO_DISTRIBUTED_TOPIC=video-distributed-storage-topic
    DISTRIBUTED_STORAGE_BLOCKCHAIN_TOPIC=distributed-storage-blockchain-topic
    BLOCKCHAIN_LOGGER_TOPIC=blockchain-logger-topic

    stop_kafka

    docker-compose -f kafka/docker-compose.yaml up -d

    create_topic $VIDEO_DISTRIBUTED_TOPIC
    create_topic $DISTRIBUTED_STORAGE_BLOCKCHAIN_TOPIC
    create_topic $BLOCKCHAIN_LOGGER_TOPIC
}
