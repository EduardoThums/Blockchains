#!/bin/bash

source ipfs/ipfs_setup.sh
source kafka/kafka_setup.sh

CHOICE=$1

case $CHOICE in
    start)
        # ./bigchaindb/start.sh
        start_ipfs
        start_kafka
    ;;
    
    stop)
        # ./bigchaindb/stop.sh
        stop_ipfs
        stop_kafka
    ;;
    
    *)
        echo "Invalid command"
    ;;
esac