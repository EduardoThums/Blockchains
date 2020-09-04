#!/bin/bash

source bigchaindb/bigchaindb_setup.sh
source swarm/swarm_setup.sh
source kafka/kafka_setup.sh

CHOICE=$1

case $CHOICE in
    start)
        start_bigchaindb
        start_swarm
        start_kafka
    ;;
    
    stop)
        stop_bigchaindb
        stop_swarm
        stop_kafka
    ;;
    
    *)
        echo "Invalid command"
    ;;
esac