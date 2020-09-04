#!/bin/bash

stop-ipfs () {
    docker-compose -f ipfs/docker-compose.yaml down --volumes --remove-orphan
}

start-ipfs () {
    stop-ipfs

    docker-compose -f ipfs/docker-compose.yaml up -d    
}
