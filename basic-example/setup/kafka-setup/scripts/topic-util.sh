#!/bin/bash

createTopic(){
    docker-compose exec kafka0 kafka-topics --create --topic $TOPIC_NAME --partitions 1 --replication-factor 1 --if-not-exists --zookeeper localhost:22181
}
