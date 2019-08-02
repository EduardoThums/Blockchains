#!/bin/bash

CHANNEL_NAME="$1"
# Path to java new fabcar chaincode
CC_SRC_PATH="/opt/gopath/src/github.com/chaincode/java/"

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
	for peer in 0 1 2 3; do
	joinChannelWithRetry $peer
	echo "===================== peer${peer}.org1 joined channel '$CHANNEL_NAME' ===================== "
	sleep $DELAY
	echo
	done
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
echo "Installing chaincode on peer0.org1..."
installChaincode 0

# Instantiate chaincode on peer0.org1
echo "Instantiating chaincode on peer0.org1..."
instantiateChaincode 0

# # Query chaincode on peer0.org1
# echo "Querying chaincode on peer0.org1..."
# chaincodeQuery 0 1 100

# # Invoke chaincode on peer0.org1 and peer0.org2
# echo "Sending invoke transaction on peer0.org1 peer0.org2..."
# chaincodeInvoke 0 1 0 2

# ## Install chaincode on peer1.org2
# echo "Installing chaincode on peer1.org2..."
# installChaincode 1 2

# # Query on chaincode on peer1.org2, check if the result is 90
# echo "Querying chaincode on peer1.org2..."
# chaincodeQuery 1 2 90
