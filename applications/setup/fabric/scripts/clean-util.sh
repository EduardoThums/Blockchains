#!/bin/bash

function removeUnwantedImages() {
  #dev-peer.*.${CHAINCODE_NAME}.*
  DOCKER_IMAGE_IDS=$(docker images | awk '($1 ~ /dev-peer.*.*.*/) {print $3}')
  if [ -z "$DOCKER_IMAGE_IDS" -o "$DOCKER_IMAGE_IDS" == " " ]; then
    echo "---- No images available for deletion ----"
  else
    docker rmi -f $DOCKER_IMAGE_IDS
  fi
}

function clearContainers() {
  CONTAINER_IDS=$(docker ps -a | awk '($2 ~ /dev-peer.*.mycc.*/) {print $1}')
  if [ -z "$CONTAINER_IDS" -o "$CONTAINER_IDS" == " " ]; then
    echo "---- No containers available for deletion ----"
  else
    docker rm -f $CONTAINER_IDS
  fi
}

removeContainers(){
    docker-compose down --volumes --remove-orphan

    #Cleanup the chaincode containers
    clearContainers

    #Cleanup images
    removeUnwantedImages
}