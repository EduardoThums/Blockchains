#!/bin/bash

source swarm/swarm_setup.sh
source kafka/kafka_setup.sh

CHOICE=$1

case $CHOICE in
    start)
        # ./bigchaindb/start.sh
        start_swarm
        start_kafka
    ;;
    
    stop)
        # ./bigchaindb/stop.sh
        stop_swarm
        stop_kafka
    ;;
    
    *)
        echo "Invalid command"
    ;;
esac