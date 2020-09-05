#!/bin/bash

source ipfs/ipfs_setup.sh
source kafka/kafka_setup.sh

CHOICE=$1

get_bigchaindb_ports() {
    echo "BigchainDB containers ports:"
    echo $(docker ps --format "{{.Names}} {{.Ports}}" | ag bigchaindb | awk '{print $2}' | grep -o ':[0-9]\+')
}

case $CHOICE in
    start)
        echo "WARNING: BigchainDB setup MUST be started with stack.sh script using sudo"
        start_ipfs
        start_kafka
    ;;
    
    stop)
        echo "WARNING: BigchainDB setup MUST be stopped with unstack.sh script using sudo"
        stop_ipfs
        stop_kafka
    ;;
    
    *)
        echo "Invalid command"
    ;;
esac

get_bigchaindb_ports
