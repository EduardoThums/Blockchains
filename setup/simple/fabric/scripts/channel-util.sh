#!/bin/bash

CHANNEL_NAME="$1"
CC_SRC_PATH="/opt/gopath/src/github.com/chaincode/java/"
CHAINCODE_LANGUAGE="java"
CHAINCODE_NAME="videoassetcc"

COUNTER=1
MAX_RETRY=10
DELAY=3
TIMEOUT=10

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

echo "Creating channel..."
createChannel

echo "Having all peers join the channel..."
joinChannel

echo "Updating anchor peers for org1..."
updateAnchorPeers 0

echo "Installing chaincode on all peers"
installChaincode 0 1.0

echo "Instantiating chaincode on peer0.org1..."
instantiateChaincode 0 1.0