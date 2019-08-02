#!/bin/bash

CHANNEL_NAME=mychannel

# Import crypto-config ustil 
. scripts/crypto-util.sh

clearContainers() {
  CONTAINER_IDS=$(docker ps -a | awk '($2 ~ /dev-peer.*.mycc.*/) {print $1}')
  if [ -z "$CONTAINER_IDS" -o "$CONTAINER_IDS" == " " ]; then
    echo "---- No containers available for deletion ----"
  else
    docker rm -f $CONTAINER_IDS
  fi
}



removeContainers(){
    docker-compose down --volumes --remove-orphan

    #Cleanup the chaincode containers
    clearContainers

    #Cleanup images
    removeUnwantedImages

    # remove orderer block and other channel configuration transactions and certs
    rm -rf channel-artifacts/*.block channel-artifacts/*.tx crypto-config ./org3-artifacts/crypto-config/ channel-artifacts/org3.json
}

#Stop and remove previous containers
echo -e "\nRemove previous networks"
removeContainers

# Generate general crypto config
generateCerts
generateChannelArtifacts

# Export private ca key
exportPrivateCaKey

#Start network
docker-compose up -d

docker exec cli scripts/channel-util.sh $CHANNEL_NAME