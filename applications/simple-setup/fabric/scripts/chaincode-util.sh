#!/bin/bash

. scripts/peer-util.sh

CHANNEL_NAME="mychannel"
CC_SRC_PATH="/opt/gopath/src/github.com/chaincode/java/"
CHAINCODE_LANGUAGE="java"
CHAINCODE_NAME="videoassetcc"
CHAINCODE_VERSION=$1

peer chaincode upgrade -o orderer.example.com:7050 \
    -C $CHANNEL_NAME \
    -n $CHAINCODE_NAME \
    -l $CHAINCODE_LANGUAGE \
    -v $CHAINCODE_VERSION \
    -p $CC_SRC_PATH \
    -c '{"Args":["init"]}' \
    -P "AND ('Org1MSP.peer')"
