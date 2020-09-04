#!/bin/bash

stop_bigchaindb () {
    docker-compose -f bigchaindb/docker-compose.yaml down --volumes --remove-orphan
}

start_bigchaindb () {
    stop_bigchaindb

    docker-compose -f bigchaindb/docker-compose.yaml up -d
}
