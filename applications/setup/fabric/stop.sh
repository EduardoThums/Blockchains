#!/bin/bash

# Import clean-util functions
. scripts/clean-util.sh

#Stop and remove previous containers
echo -e "Remove previous networks"
removeContainers