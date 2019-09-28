#!/bin/bash

CHAINCODE_VERSION=$1

docker exec cli scripts/chaincode-util.sh $CHAINCODE_VERSION