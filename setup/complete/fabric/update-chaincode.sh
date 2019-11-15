#!/bin/bash

CHAINCODE_VERSION=$(docker ps -a | awk '($2 ~ /dev-peer0.*.*.*/)' | wc -l)

docker exec cli scripts/chaincode-util.sh 1.$CHAINCODE_VERSION