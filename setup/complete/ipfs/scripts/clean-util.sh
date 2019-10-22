#!/bin/bash

removeContainers(){
    docker-compose down --volumes --remove-orphan
}