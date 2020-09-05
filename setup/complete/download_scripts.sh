#!/bin/bash

curl -fOL https://raw.githubusercontent.com/bigchaindb/bigchaindb/master/pkg/scripts/stack.sh
curl -fOL https://raw.githubusercontent.com/bigchaindb/bigchaindb/master/pkg/scripts/unstack.sh

mv stack.sh unstack.sh bigchaindb/

chmod 777 bigchaindb/stack.sh
chmod 777 bigchaindb/unstack.sh

echo "Use STACK_BRANCH=update-deps"