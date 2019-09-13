#!/bin/bash

PASSWORD=producer-topic
IMAGE_TAG=latest

docker run -d -p 8501:8500/tcp \
    -e PASSWORD=$PASSWORD \
    -t ethdevops/swarm:$IMAGE_TAG \
    --httpaddr=0.0.0.0

