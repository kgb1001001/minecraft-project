#!/bin/bash
# cleanup script for containers on bluemix
if [ "$#" = 2 ]; then
        sudo ice stop $1
        sudo ice ip unbind $2 $1
        sudo ice rm $1
        exit 0
else
        echo "ERROR: cleanup takes 2 parameters"
        echo "usage cleanup container_name container_ip"
        exit 1
fi
