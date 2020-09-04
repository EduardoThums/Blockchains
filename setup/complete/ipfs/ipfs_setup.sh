#!/bin/bash

stop_ipfs () {
    docker-compose -f ipfs/docker-compose.yaml down --volumes --remove-orphan
}

start_ipfs () {
    stop_ipfs

    docker-compose -f ipfs/docker-compose.yaml up -d    
}
