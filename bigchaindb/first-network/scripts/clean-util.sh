#!/bin/bash

removeContainers(){
    docker-compose down --volumes --remove-orphan
}

removeVolumes(){
    rm -Rf $HOME/docker/volumes/bigchaindb
}