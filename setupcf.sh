#!/bin/bash
# setup script
if [ "$#" -ne 3 ]; then
        echo "ERROR: setup takes 3 parameters"
        echo "usage setup local_directory container_ip container_port"
        exit 1
else
        echo "Phase 1->building container locally"
        sudo docker build -t kbrown/$1 $1
        echo "Phase 2->tagging container"
        sudo docker tag $1 \
registry.ng.bluemix.net/kbrown/$1
        echo "Phase 3->pushing container to bluemix"
        sudo docker push registry.ng.bluemix.net/kbrown/$1
        echo "Phase 4->validating image list"
        sudo cf ic images | grep $1
        echo "Phase 5->running container on bluemix"
        sleep 25s
        sudo cf ic run --memory 2048 --name $1 -p $3 \
registry.ng.bluemix.net/kbrown/$1:latest
        echo "Phase 6->binding IP address"
        sudo cf ic ip bind $1 $2
        exit 0
fi
