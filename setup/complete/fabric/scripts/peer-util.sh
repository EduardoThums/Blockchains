#!/bin/bash

ORDERER_CA=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem
CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp
CORE_PEER_LOCALMSPID="Org1MSP"
CORE_PEER_TLS_ROOTCERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt

verifyResult() {
  if [ $1 -ne 0 ]; then
    echo "!!!!!!!!!!!!!!! "$2" !!!!!!!!!!!!!!!!"
    echo "========= ERROR !!! FAILED to execute End-2-End Scenario ==========="
    exit 1
  fi
}

setGlobals() {
  PEER=$1
  
  if [ $PEER -eq 0 ]; then
    CORE_PEER_ADDRESS=peer0.org1.example.com:7051
  elif [ $PEER -eq 1 ]; then
    CORE_PEER_ADDRESS=peer1.org1.example.com:8051
  elif [ $PEER -eq 2 ]; then
    CORE_PEER_ADDRESS=peer2.org1.example.com:9051
  else
    CORE_PEER_ADDRESS=peer3.org1.example.com:10051
  fi
}

joinChannelWithRetry() {
  PEER=$1
  setGlobals $PEER

  peer channel join -b $CHANNEL_NAME.block >&log.txt
  res=$?
  cat log.txt
  
  if [ $res -ne 0 -a $COUNTER -lt $MAX_RETRY ]; then
    COUNTER=$(expr $COUNTER + 1)
    echo "peer${PEER}.org${ORG} failed to join the channel, Retry after $DELAY seconds"
    sleep $DELAY
    joinChannelWithRetry $PEER
  else
    COUNTER=1
  fi

  verifyResult $res "After $MAX_RETRY attempts, peer${PEER}.org1 has failed to join channel '$CHANNEL_NAME' "
}

updateAnchorPeers() {
  PEER=$1
  setGlobals $PEER

  peer channel update -o orderer.example.com:7050 -c $CHANNEL_NAME -f ./channel-artifacts/${CORE_PEER_LOCALMSPID}anchors.tx  >&log.txt
  res=$?
  cat log.txt

  verifyResult $res "Anchor peer update failed"
  echo "===================== Anchor peers updated for org '$CORE_PEER_LOCALMSPID' on channel '$CHANNEL_NAME' ===================== "
  sleep $DELAY
}

installChaincode() {
  PEER=$1
  setGlobals $PEER
  
  set -x
  peer chaincode install -n $CHAINCODE_NAME -v 1.0 -l $CHAINCODE_LANGUAGE -p ${CC_SRC_PATH} >&log.txt
  res=$?
  set +x
  cat log.txt
  
  verifyResult $res "Chaincode installation on peer${PEER}.org1 has failed"
  echo "===================== Chaincode is installed on peer${PEER}.org1 ===================== "
}


instantiateChaincode() {
  PEER=$1
  setGlobals $PEER

  set -x
  peer chaincode instantiate -o orderer.example.com:7050 -C $CHANNEL_NAME -n $CHAINCODE_NAME -l $CHAINCODE_LANGUAGE -v 1.0 -c '{"Args":["init"]}' -P "AND ('Org1MSP.peer')" >&log.txt
  res=$?
  set +x
  cat log.txt

  verifyResult $res "Chaincode instantiation on peer${PEER}.org1 on channel '$CHANNEL_NAME' failed"
  echo "===================== Chaincode is instantiated on peer${PEER}.org1 on channel '$CHANNEL_NAME' ===================== "
}