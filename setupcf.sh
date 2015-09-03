#!/bin/bash
# setup script
N="kgb1001001"
if [ "$#" -ne 5 ]; then
        echo "ERROR: setupcf takes 5 parameters"
        echo "usage setup local_directory image_name container_name container_ip container_port"
        exit 1
else
        echo "Phase 1->building container locally"
        sudo docker build -t $2 $1
        echo "Phase 2->tagging container"
        sudo docker tag $2 \
registry.ng.bluemix.net/$N/$2
        echo "Phase 3->pushing container to bluemix"
        sudo docker push registry.ng.bluemix.net/$N/$2
        echo "Phase 4->validating image list"
        sudo cf ic images | grep $1
        echo "Phase 5->running container on bluemix"
        sleep 45s
        sudo cf ic run --name=$3 -publish=$5 \
registry.ng.bluemix.net/$N/$1:latest
        echo "Phase 6->binding IP address"
        sudo cf ic ip bind $3 $4
        exit 0
fi
