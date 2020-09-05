#!/bin/bash

source ipfs/ipfs_setup.sh
source kafka/kafka_setup.sh

CHOICE=$1

get_bigchaindb_ports() {
    docker ps --format "{{.Names}} {{.Ports}}" | ag bigchaindb | awk '{print $2}' | grep -o ':[0-9]\+'
}

case $CHOICE in
    start)
        ./bigchaindb/start.sh
        start_ipfs
        start_kafka
    ;;
    
    stop)
        ./bigchaindb/stop.sh
        stop_ipfs
        stop_kafka
    ;;
    
    *)
        echo "Invalid command"
    ;;
esac

get_bigchaindb_ports