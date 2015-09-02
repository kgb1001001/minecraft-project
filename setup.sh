#!/bin/bash
# setup script
if [ "$#" -ne 3 ]; then
        echo "ERROR: setup takes 3 parameters"
        echo "usage setup local_directory container_ip container_port"
        exit 1
else
        echo "Phase 1->building container locally"
        sudo ice --local build -t  $1 $1
        echo "Phase 2->tagging container"
        sudo ice --local tag -f $1 \
registry-ice.ng.bluemix.net/kbrown/$1
        echo "Phase 3->pushing container to bluemix"
        sudo ice --local push registry-ice.ng.bluemix.net/kbrown/$1
        echo "Phase 4->validating image list"
        sudo ice images | grep $1
        echo "Phase 5->running container on bluemix"
        sleep 25s
        sudo ice run --memory 2048 --name $1 -p $3 \
kbrown/$1:latest
        echo "Phase 6->binding IP address"
        sudo ice ip bind $1 $2
        exit 0
fi
