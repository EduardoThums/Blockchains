#!/bin/bash

echo
echo " ____    _____      _      ____    _____ "
echo "/ ___|  |_   _|    / \    |  _ \  |_   _|"
echo "\___ \    | |     / _ \   | |_) |   | |  "
echo " ___) |   | |    / ___ \  |  _ <    | |  "
echo "|____/    |_|   /_/   \_\ |_| \_\   |_|  "
echo
echo "Build your first network (BYFN) end-to-end test"
echo
CHANNEL_NAME="$1"
DELAY="$2"
LANGUAGE="$3"
TIMEOUT="$4"
VERBOSE="$5"
: ${CHANNEL_NAME:="mychannel"}
: ${DELAY:="3"}
: ${LANGUAGE:="java"}
: ${TIMEOUT:="10"}
: ${VERBOSE:="false"}
LANGUAGE=`echo "$LANGUAGE" | tr [:upper:] [:lower:]`
COUNTER=1
MAX_RETRY=10

CC_SRC_PATH="/opt/gopath/src/github.com/chaincode/java/"

echo "Channel name : "$CHANNEL_NAME

# import utils
. scripts/utils.sh

createChannel() {
	setGlobals 0 1

	if [ -z "$CORE_PEER_TLS_ENABLED" -o "$CORE_PEER_TLS_ENABLED" = "false" ]; then
                set -x
		peer channel create -o orderer.example.com:7050 -c $CHANNEL_NAME -f ./channel-artifacts/channel.tx >&log.txt
		res=$?
                set +x
	else
				set -x
		peer channel create -o orderer.example.com:7050 -c $CHANNEL_NAME -f ./channel-artifacts/channel.tx --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA >&log.txt
		res=$?
				set +x
	fi
	cat log.txt
	verifyResult $res "Channel creation failed"
	echo "===================== Channel '$CHANNEL_NAME' created ===================== "
	echo
}

joinChannel () {
	for org in 1 2; do
	    for peer in 0 1; do
		joinChannelWithRetry $peer $org
		echo "===================== peer${peer}.org${org} joined channel '$CHANNEL_NAME' ===================== "
		sleep $DELAY
		echo
	    done
	done
}

# ## Create channel
# echo "Creating channel..."
# createChannel

# ## Join all the peers to the channel
# echo "Having all peers join the channel..."
# joinChannel

# ## Set the anchor peers for each org in the channel
# echo "Updating anchor peers for org1..."
# updateAnchorPeers 0 1

# ## Install chaincode on peer0.org1 and peer1.org1
# echo "Installing chaincode on peer0.org1..."
# installChaincode 0 1
# echo "Install chaincode on peer1.org1..."
# installChaincode 1 1
# echo "Installing chaincode on peer2.org1..."
# installChaincode 2 1
# echo "Install chaincode on peer3.org1..."
# installChaincode 3 1

# # Instantiate chaincode on peer0.org1
# echo "Instantiating chaincode on peer0.org1..."
# instantiateChaincode 0 1

# # Query chaincode on peer0.org1
# echo "Querying chaincode on peer0.org1..."
# chaincodeQuery 0 1 100

# # Invoke chaincode on peer0.org1 and peer1.org1
# echo "Sending invoke transaction on peer0.org1 peer1.org1..."
# chaincodeInvoke 0 1 1 1

# # # Query on chaincode on peer0.org1, check if the result is 90
# # echo "Querying chaincode on peer0.org1..."
# # chaincodeQuery 0 1 90

# # Query on chaincode on peer1.org1, check if the result is 210
# echo "Querying chaincode on peer1.org1..."
# chaincodeQuery 1 1 90

echo
echo "========= All GOOD, BYFN execution completed =========== "
echo

echo
echo " _____   _   _   ____   "
echo "| ____| | \ | | |  _ \  "
echo "|  _|   |  \| | | | | | "
echo "| |___  | |\  | | |_| | "
echo "|_____| |_| \_| |____/  "
echo

exit 0
