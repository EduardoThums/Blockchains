#!/bin/bash

PASSWORD=123
IMAGE_TAG=latest

docker stop swarm0
docker rm swarm0

docker run -d -p 8501:8500/tcp \
    --name swarm0 \
    -e PASSWORD=$PASSWORD \
    -t ethdevops/swarm:$IMAGE_TAG \
    --httpaddr=0.0.0.0

