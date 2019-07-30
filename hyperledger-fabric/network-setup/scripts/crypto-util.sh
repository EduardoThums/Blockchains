#!/bin/bash
# export PATH=${PWD}/./bin:${PWD}:$PATH

function generateCerts() {
  echo "##########################################################"
  echo "##### Generate certificates using cryptogen tool #########"
  echo "##########################################################"

  if [ -d "crypto-config" ]; then
    rm -Rf crypto-config/
  fi
  
  ./bin/cryptogen generate --config=./crypto-config.yaml
  res=$?
  
  if [ $res -ne 0 ]; then
    echo "Failed to generate certificates..."
    exit 1
  fi 
}

function generateChannelArtifacts() {
  echo "##########################################################"
  echo "#########  Generating Orderer Genesis block ##############"
  echo "##########################################################"

  ./bin/configtxgen -profile OneOrgOrdererGenesis -channelID byfn-sys-channel -outputBlock ./channel-artifacts/genesis.block
  res=$?

  if [ $res -ne 0 ]; then
    echo "Failed to generate orderer genesis block..."
    exit 1
  fi

  echo "#################################################################"
  echo "### Generating channel configuration transaction 'channel.tx' ###"
  echo "#################################################################"

  ./bin/configtxgen -profile OneOrgChannel -outputCreateChannelTx ./channel-artifacts/channel.tx -channelID $CHANNEL_NAME
  res=$?

  if [ $res -ne 0 ]; then
    echo "Failed to generate channel configuration transaction..."
    exit 1
  fi

  echo "#################################################################"
  echo "#######    Generating anchor peer update for Org1MSP   ##########"
  echo "#################################################################"

  ./bin/configtxgen -profile OneOrgChannel -outputAnchorPeersUpdate ./channel-artifacts/Org1MSPanchors.tx -channelID $CHANNEL_NAME -asOrg Org1MSP
  res=$?

  if [ $res -ne 0 ]; then
    echo "Failed to generate anchor peer update for Org1MSP..."
    exit 1
  fi
}

function exportPrivateCaKey(){
  export BYFN_CA1_PRIVATE_KEY=$(cd ./crypto-config/peerOrganizations/org1.example.com/ca && ls *_sk)
}