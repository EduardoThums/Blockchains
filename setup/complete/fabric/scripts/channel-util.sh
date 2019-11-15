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
	for peer in 0 1 2 3; do
	joinChannelWithRetry $peer
	echo "===================== peer${peer}.org1 joined channel '$CHANNEL_NAME' ===================== "
	sleep $DELAY
	echo
	done
}

installChaincodeOnAllPeers () {
	for peer in 0 1 2 3; do
	installChaincode $peer 1.0
	done
}

echo "Creating channel..."
createChannel

echo "Having all peers join the channel..."
joinChannel

echo "Installing chaincode on all peers..."
installChaincodeOnAllPeers

echo "Instantiating chaincode on peer0 peers..."
instantiateChaincode 0

echo "Update peer0 anchor..."
updateAnchorPeers 0


