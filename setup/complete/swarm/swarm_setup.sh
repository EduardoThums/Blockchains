#!/bin/bash

stop_swarm() {
    docker rm -f swarm0
}

start_swarm() {
    PASSWORD=250820000aA

    stop_swarm

    docker run -d -p 8501:8500/tcp \
        --name swarm0 \
        -e PASSWORD=$PASSWORD \
        -t ethdevops/swarm \
        --httpaddr=0.0.0.0
}
