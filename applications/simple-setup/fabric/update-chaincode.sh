#!/bin/bash

CHAINCODE_VERSION=$1

. scripts/clean-util.sh

docker exec cli scripts/chaincode-util.sh $CHAINCODE_VERSION