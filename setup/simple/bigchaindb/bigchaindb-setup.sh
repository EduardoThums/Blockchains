#!/bin/bash

stop-bigchaindb () {
    docker-compose -f bigchaindb/docker-compose.yaml down --volumes --remove-orphan
}

start-bigchaindb () {
    stop-bigchaindb

    docker-compose -f bigchaindb/docker-compose.yaml up -d
}
