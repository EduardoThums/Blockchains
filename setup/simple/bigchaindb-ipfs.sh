#!/bin/bash

source ipfs/ipfs-setup.sh
source bigchaindb/bigchaindb-setup.sh

CHOICE=$1

case $CHOICE in
    start)
        start-bigchaindb
        start-ipfs
    ;;
    
    stop)
        stop-bigchaindb
        stop-ipfs
    ;;
    
    *)
        echo "Invalid command"
    ;;
esac