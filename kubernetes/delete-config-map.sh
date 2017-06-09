#!/bin/bash

CONFIGURATION=amqp-bare-metal-producer-configuration

DELETE="kubectl delete configmap ${CONFIGURATION}"

echo ${DELETE}
${DELETE}
