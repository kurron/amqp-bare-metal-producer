#!/bin/bash

HOST=${1:-localhost}
VHOST=${2:-/}
USERNAME=${3:-guest}
PASSWORD=${4:-guest}
MESSAGE_COUNT=${5:-2500}
PAYLOAD_SIZE=${6:-1024}

CMD="docker run \
            --cpus 1 \
            --env consumer_modvalue=1000 \
            --env spring_rabbitmq_host=${HOST} \
            --env spring_rabbitmq_virtual-host=${VHOST} \
            --env spring.rabbitmq.username=${USERNAME} \
            --env spring.rabbitmq.password=${PASSWORD} \
            --interactive  \
            --name amqp-producer \
            --network host \
            --rm \
            --tty \
            kurron/amqp-bare-metal-producer:latest \
            --number-of-messages=${MESSAGE_COUNT} \
            --payload-size=${PAYLOAD_SIZE}"

echo ${CMD}
${CMD}