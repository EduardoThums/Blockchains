#!/bin/bash

CHANNEL_NAME=mychannel
SYS_CHANNEL=byfn-sys-channel

source fabric/scripts/crypto-util.sh

stop_fabric() {
    DOCKER_IMAGE_IDS=$(docker images | awk '($1 ~ /dev-peer.*.*.*/) {print $3}')
    if [ -z "$DOCKER_IMAGE_IDS" -o "$DOCKER_IMAGE_IDS" == " " ]; then
        echo ""
    else
        docker rmi -f $DOCKER_IMAGE_IDS
    fi

    docker-compose -f fabric/docker-compose.yaml down --volumes --remove-orphan

    CONTAINER_IDS=$(docker ps -a | awk '($2 ~ /dev-peer.*.*.*/) {print $1}')
    if [ -z "$CONTAINER_IDS" -o "$CONTAINER_IDS" == " " ]; then
        echo ""
    else
        docker rm -f $CONTAINER_IDS
    fi
}

start_fabric() {
    stop_fabric

    generate_certs
    generate_channel_artifacts
    export_private_ca_key

    docker-compose -f fabric/docker-compose.yaml up -d

    docker exec cli scripts/channel-util.sh $CHANNEL_NAME
}
