#!/bin/bash

source fabric/fabric_setup.sh
source ipfs/ipfs_setup.sh
source kafka/kafka_setup.sh

CHOICE=$1

case $CHOICE in
    start)
        start_fabric
        start_ipfs
        start_kafka
    ;;
    
    stop)
        stop_fabric
        stop_ipfs
        stop_kafka
    ;;
    
    *)
        echo "Invalid command"
    ;;
esac