#!/bin/bash

generate_certs() {
  echo "##########################################################"
  echo "##### Generate certificates using cryptogen tool #########"
  echo "##########################################################"

  if [ -d "fabric/crypto-config" ]; then
    rm -Rf fabric/crypto-config/
  fi
  
  ./fabric/bin/cryptogen generate --config=./fabric/crypto-config.yaml --output=fabric/crypto-config
  res=$?
  
  if [ $res -ne 0 ]; then
    echo "Failed to generate certificates..."
    exit 1
  fi 
}

generate_channel_artifacts() {
  echo "##########################################################"
  echo "#########  Generating Orderer Genesis block ##############"
  echo "##########################################################"

  ./fabric/bin/configtxgen -profile OneOrgOrdererGenesis -configPath fabric/ -channelID $SYS_CHANNEL -outputBlock fabric/channel-artifacts/genesis.block
  res=$?

  if [ $res -ne 0 ]; then
    echo "Failed to generate orderer genesis block..."
    exit 1
  fi

  echo "#################################################################"
  echo "### Generating channel configuration transaction 'channel.tx' ###"
  echo "#################################################################"

  ./fabric/bin/configtxgen -profile OneOrgChannel -configPath fabric/ -outputCreateChannelTx fabric/channel-artifacts/channel.tx -channelID $CHANNEL_NAME
  res=$?

  if [ $res -ne 0 ]; then
    echo "Failed to generate channel configuration transaction..."
    exit 1
  fi

  echo "#################################################################"
  echo "#######    Generating anchor peer update for Org1MSP   ##########"
  echo "#################################################################"

  ./fabric/bin/configtxgen -profile OneOrgChannel -configPath fabric/ -outputAnchorPeersUpdate fabric/channel-artifacts/Org1MSPanchors.tx -channelID $CHANNEL_NAME -asOrg Org1MSP
  res=$?

  if [ $res -ne 0 ]; then
    echo "Failed to generate anchor peer update for Org1MSP..."
    exit 1
  fi
}

export_private_ca_key(){
  export BYFN_CA1_PRIVATE_KEY=$(cd ./fabric/crypto-config/peerOrganizations/org1.example.com/ca && ls *_sk)
}