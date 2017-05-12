#!/bin/bash

# use the time as a tag
UNIXTIME=$(date +%s)

# docker tag SOURCE_IMAGE[:TAG] TARGET_IMAGE[:TAG]
docker tag amqpbaremetalproducer_producer:latest kurron/amqp-bare-metal-producer:latest
docker tag amqpbaremetalproducer_producer:latest kurron/amqp-bare-metal-producer:${UNIXTIME}
docker images

# Usage:  docker push [OPTIONS] NAME[:TAG]
docker push kurron/amqp-bare-metal-producer:latest
docker push kurron/amqp-bare-metal-producer:${UNIXTIME}
