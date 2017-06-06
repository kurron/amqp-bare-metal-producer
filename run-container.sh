#!/bin/bash

HOST=${1:-localhost}
VHOST=${2:-/}
USERNAME=${3:-guest}
PASSWORD=${4:-guest}
MESSAGE_COUNT=${5:-524288}
PAYLOAD_SIZE=${6:-1024}
THREAD_COUNT=${7:-32}

CMD="docker run \
            --cpus 1 \
            --env consumer_modvalue=1000 \
            --env spring_rabbitmq_host=${HOST} \
            --env spring_rabbitmq_virtual-host=${VHOST} \
            --env spring_rabbitmq_username=${USERNAME} \
            --env spring_rabbitmq_password=${PASSWORD} \
            --interactive  \
            --name amqp-producer \
            --network host \
            --rm \
            kurron/amqp-bare-metal-producer:latest \
            --number-of-messages=${MESSAGE_COUNT} \
            --payload-size=${PAYLOAD_SIZE} \
            --thread-count=${THREAD_COUNT}"

echo ${CMD}
${CMD}
