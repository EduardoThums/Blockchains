#!/bin/bash

CHANNEL_NAME="$1"
CC_SRC_PATH="/opt/gopath/src/github.com/chaincode/java/"
CHAINCODE_LANGUAGE="java"

CHAINCODE_NAME="videoassetcc"

COUNTER=1
MAX_RETRY=10
DELAY=3
TIMEOUT=10

#Import peer utils
. scripts/peer-util.sh

createChannel() {
	setGlobals 0

	peer channel create -o orderer.example.com:7050 -c $CHANNEL_NAME -f ./channel-artifacts/channel.tx >&log.txt
	res=$?

	cat log.txt
	verifyResult $res "Channel creation failed"
	echo "===================== Channel '$CHANNEL_NAME' created ===================== "
}

joinChannel () {
	joinChannelWithRetry 0
	echo "===================== peer0.org1 joined channel '$CHANNEL_NAME' ===================== "
	sleep $DELAY
	echo
}

## Create channel
echo "Creating channel..."
createChannel

## Join all the peers to the channel
echo "Having all peers join the channel..."
joinChannel

## Set the anchor peers for each org in the channel
echo "Updating anchor peers for org1..."
updateAnchorPeers 0

## Install chaincode on peer0.org1
echo "Installing chaincode on all peers"
installChaincode 0

# Instantiate chaincode on peer0.org1
echo "Instantiating chaincode on peer0.org1..."
instantiateChaincode 0