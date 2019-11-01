#!/bin/bash

createTopic(){
    TOPIC_NAME=$1

    docker-compose exec kafka0 kafka-topics --create --topic $TOPIC_NAME --partitions 1 --replication-factor 1 --if-not-exists --zookeeper localhost:22181 >&log.txt
    res=$?

    while [ $res -ne 0 ]; do
        docker-compose exec kafka0 kafka-topics --create --topic $TOPIC_NAME --partitions 1 --replication-factor 1 --if-not-exists --zookeeper localhost:22181 >&log.txt
        res=$?
    done

    echo "Topic $TOPIC_NAME created"
    
    rm -f log.txt
}