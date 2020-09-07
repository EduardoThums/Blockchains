#!/bin/bash

sudo rm -rf bigchaindb/*

curl -fL https://raw.githubusercontent.com/bigchaindb/bigchaindb/master/pkg/scripts/stack.sh -o bigchaindb/stack.sh
curl -fL https://raw.githubusercontent.com/bigchaindb/bigchaindb/master/pkg/scripts/unstack.sh -o bigchaindb/unstack.sh

chmod 777 bigchaindb/stack.sh
chmod 777 bigchaindb/unstack.sh

echo "Use STACK_BRANCH=update-deps"