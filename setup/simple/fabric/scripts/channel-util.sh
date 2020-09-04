#!/bin/bash

source scripts/peer-util.sh


CHANNEL_NAME="$1"
CC_SRC_PATH="/opt/gopath/src/github.com/chaincode/java/"
CHAINCODE_LANGUAGE="java"
CHAINCODE_NAME="videoassetcc"

COUNTER=1
MAX_RETRY=10
DELAY=3
TIMEOUT=10

create_channel() {
	setGlobals 0

	peer channel create -o orderer.example.com:7050 -c $CHANNEL_NAME -f ./channel-artifacts/channel.tx >&log.txt
	res=$?

	rm log.txt

	verifyResult $res "Channel creation failed"
	echo "===================== Channel '$CHANNEL_NAME' created ===================== "
}

join_channel () {
	join_channel_with_retry 0
	echo "===================== peer0.org1 joined channel '$CHANNEL_NAME' ===================== "
	sleep $DELAY
	echo
}

echo "Creating channel..."
create_channel

echo "Having all peers join the channel..."
join_channel

echo "Updating anchor peers for org1..."
update_anchor_peers 0

echo "Installing chaincode on all peers"
install_chaincode 0 1.0

echo "Instantiating chaincode on peer0.org1..."
instantiate_chaincode 0 1.0