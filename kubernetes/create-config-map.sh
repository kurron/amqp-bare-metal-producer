#!/bin/bash

# Creates Kubernetes Config Maps to hold the various configuration parameters for the jobs

HOST=${1:-localhost}
VHOST=${2:-/}
USERNAME=${3:-guest}
PASSWORD=${4:-guest}
MESSAGE_COUNT=${5:-1024}
PAYLOAD_SIZE=${6:-1024}
THREAD_COUNT=${7:-32}
MODVALUE=${8:-10000}

CONFIGURATION=amqp-bare-metal-producer-configuration

CREATE="kubectl create configmap ${CONFIGURATION} \
            --from-literal modvalue=${MODVALUE} \
            --from-literal host=${HOST} \
            --from-literal virtual-host=${VHOST} \
            --from-literal username=${USERNAME} \
            --from-literal password=${PASSWORD} \
            --from-literal number-of-messages=${MESSAGE_COUNT} \
            --from-literal payload-size=${PAYLOAD_SIZE} \
            --from-literal thread-count=${THREAD_COUNT}"

echo ${CREATE}
${CREATE}

GET="kubectl get configmaps ${CONFIGURATION} -o yaml"
echo ${GET}
${GET}
